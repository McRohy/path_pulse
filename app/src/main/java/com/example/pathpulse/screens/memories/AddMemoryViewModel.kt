package com.example.pathpulse.screens.memories

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathpulse.constans.TIMEOUT_MILLIS
import com.example.pathpulse.helpers.CountryMappers.toDetails
import com.example.pathpulse.helpers.CountryMappers.toEntity
import com.example.pathpulse.data.dataMomories.CountriesRepository
import com.example.pathpulse.data.dataMomories.CountryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel pre zaznamenanie spomienky (krajiny).
 *
 * @property countriesRepository Repozitár, cez ktorý sa vykonávajú operácie nad krajinami.
 */
class AddViewModel(private val countriesRepository: CountriesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AddUiState())

    /**
     * Stream výsledkov vyhľadávania krajín podľa zadanej query.
     *
     * reaguje na každú zmenu searchQuery v internom _uiState a pridáva 300 ms debounce,
     * aby sa nevolalo vyhľadávanie pri každom písanom znaku.DistinctUntilChanged
     * ignoruje identické dotazy. flatMapLatest spustí nové vyhľadávanie a zruší predchádzajúce,
     * ak príde nová query a stateIn premení celý flow na StateFlow
     *
     * https://medium.com/mindorks/implement-instant-search-using-kotlin-flow-operators-7bd658bdfc4b
     */
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private val searchResults: StateFlow<List<CountryEntity>> =
        _uiState
            .map { it.searchQuery }
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { countriesRepository.searchCountries(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                emptyList()
            )

    fun onImagePicked(newUri: Uri) {
        val old = _uiState.value
        val details = CountryDetails(
            id = old.countryDetails.id,
            name = old.countryDetails.name,
            description = old.countryDetails.description,
            updatedAt = old.countryDetails.updatedAt,
            rating = old.countryDetails.rating,
            imgUri = newUri.toString()
        )
        updateUiState(details, old)
    }

    /**
     * Uchováva aktuálny stav obrazovky vrátane
     * interných dát a výsledkov vyhľadávania.
     */
    val uiState: StateFlow<AddUiState> =
        combine(
            _uiState,
            searchResults
        ) { uistate, results ->
            uistate.copy(searchResults = results)
        }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                AddUiState()
            )

    /**
     * Volá sa pri zmene hodnotenia (rating) krajiny.
     *
     * @param newRating Nové hodnotenie.
     */
    fun onRatingChange(newRating: Int) {
        val old = _uiState.value
        val details = CountryDetails(
            id = old.countryDetails.id,
            name = old.countryDetails.name,
            description = old.countryDetails.description,
            updatedAt = old.countryDetails.updatedAt,
            rating = newRating,
            imgUri = old.countryDetails.imgUri
        )
        updateUiState(details, old)
    }

    /**
     * Volá sa pri zmene textu v search bare.
     *
     * @param newQuery Nový text vyhľadávania.
     */
    fun onSearchQueryChange(newQuery: String) {
        val old = _uiState.value
        _uiState.value = AddUiState(
            countryDetails = old.countryDetails,
            isEntryValid = old.isEntryValid,
            selectedCountry = old.selectedCountry,
            searchResults = old.searchResults,
            searchQuery = newQuery,
            searchActive = old.searchActive
        )
    }

    /**
     * Volá sa pri aktivácii/deaktivácii search baru.
     *
     * @param active true = aktívny mód vyhľadávania, false = neaktívny.
     */
    fun onSearchBarActiveChange(active: Boolean) {
        val old = _uiState.value
        _uiState.value = AddUiState(
            countryDetails = old.countryDetails,
            isEntryValid = old.isEntryValid,
            selectedCountry = old.selectedCountry,
            searchResults = old.searchResults,
            searchQuery = old.searchQuery,
            searchActive = active
        )
    }

    /**
     * Volá sa pri zmene popisu krajiny.
     *
     * @param newDescription Nový text popisu.
     */
    fun onDescriptionChange(newDescription: String) {
        val old = _uiState.value
        val details = CountryDetails(
            id = old.countryDetails.id,
            name = old.countryDetails.name,
            description = newDescription,
            updatedAt = old.countryDetails.updatedAt,
            rating = old.countryDetails.rating,
            imgUri = old.countryDetails.imgUri
        )
        updateUiState(details, old)
    }

    /**
     * Volá sa po výbere krajiny zo zoznamu výsledkov.
     *
     * @param country Entity krajiny, ktorú používateľ vybral.
     */
    fun onCountrySelected(country: CountryEntity) {
        val old = _uiState.value
        val fromDbCountry = country.toDetails()
        val details = CountryDetails(
            id = fromDbCountry.id,
            name = fromDbCountry.name,
            description = old.countryDetails.description,
            updatedAt = old.countryDetails.updatedAt,
            rating = old.countryDetails.rating,
            imgUri = old.countryDetails.imgUri
        )

        _uiState.value = AddUiState(
            countryDetails = details,
            isEntryValid = validateInput(details),
            selectedCountry = country,
            searchResults = old.searchResults,
            searchQuery = old.searchQuery,
            searchActive = old.searchActive
        )
    }

    /**
     * Overí, či sú vstupné polia platné: názov alebo popis nie je prázdny a rating > 0
     */
    private fun validateInput(details: CountryDetails): Boolean =
        details.name.isNotBlank() && details.description.isNotBlank() && details.rating > 0

    /**
     * Ukladanie zmien do databázy.
     * Ak je vybraná krajina a vstupy sú validné,
     * premapuje CountryDetails na CountryEntity a zavolá update v repozitári.
     */
    suspend fun save() {
        val state = _uiState.value
        if (state.selectedCountry != null && state.isEntryValid) {
            val updatedEntity = state.countryDetails.toEntity()
            countriesRepository.updateDescriptionByName(updatedEntity)
        }
    }

    /**
     * Pomocná funkcia pre aktualizáciu _uiState so zmenenými detailmi.
     */
    private fun updateUiState(details: CountryDetails, old: AddUiState) {
        _uiState.value = AddUiState(
            countryDetails = details,
            isEntryValid = validateInput(details),
            selectedCountry = old.selectedCountry,
            searchResults = old.searchResults,
            searchQuery = old.searchQuery,
            searchActive = old.searchActive
        )
    }
}

/**
 * Dátová trieda reprezentujúca stav UI v AddViewModel.
 *
 * @property countryDetails Detaily práve editovanej krajiny.
 * @property isEntryValid Označenie, či sú vstupy validné pre uloženie.
 * @property selectedCountry Zvolená krajina (entity) alebo null.
 * @property searchResults Aktuálne výsledky vyhľadávania.
 * @property searchQuery Text zadaný do search baru.
 * @property searchActive Označenie, či je režim vyhľadávania aktívny.
 */
data class AddUiState(
    val countryDetails: CountryDetails = CountryDetails(),
    val isEntryValid: Boolean = false,
    val selectedCountry: CountryEntity? = null,
    val searchResults: List<CountryEntity> = emptyList(),
    val searchQuery: String = "",
    val searchActive: Boolean = false,
)

/**
 * Dáta detailov krajiny používané v UI.
 *
 * @property id Identifikátor krajiny.
 * @property name Názov krajiny.
 * @property description Popis krajiny.
 * @property updatedAt Čas poslednej úpravy.
 * @property rating Hodnotenie krajiny.
 */
data class CountryDetails(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val updatedAt: Long = 0L,
    val rating: Int = 0,
    val imgUri: String = ""
)