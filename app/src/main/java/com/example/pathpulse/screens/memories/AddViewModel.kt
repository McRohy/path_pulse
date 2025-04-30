package com.example.pathpulse.screens.memories


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathpulse.data.dataMomories.CountriesRepository
import com.example.pathpulse.data.dataMomories.CountryEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class AddViewModel(private val countriesRepository: CountriesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AddUiState())

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

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

    fun onRatingChange(newRating: Int) {
        val old = _uiState.value
        val details = CountryDetails(
            id = old.countryDetails.id,
            name = old.countryDetails.name,
            description = old.countryDetails.description,
            updatedAt = old.countryDetails.updatedAt,
            rating = newRating
        )
        updateUiState(details, old)
    }

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

//    fun onSearchQuerySubmit() {
//
//    }

    fun onDescriptionChange(newDescription: String) {
        val old = _uiState.value
        val details = CountryDetails(
            id = old.countryDetails.id,
            name = old.countryDetails.name,
            description = newDescription,
            updatedAt = old.countryDetails.updatedAt,
            rating = old.countryDetails.rating
        )
        updateUiState(details, old)
    }

    fun onCountrySelected(country: CountryEntity) {
        val old = _uiState.value
        val fromDbCountry = country.toDetails()
        val details = CountryDetails(
            id = fromDbCountry.id,
            name = fromDbCountry.name,
            description = old.countryDetails.description,
            updatedAt = old.countryDetails.updatedAt,
            rating = old.countryDetails.rating
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

    private fun validateInput(details: CountryDetails): Boolean =
        details.name.isNotBlank() && details.description.isNotBlank() && details.rating > 0

    suspend fun save() {
        val state = _uiState.value
        if (state.selectedCountry != null && state.isEntryValid) {
            val updatedEntity = state.countryDetails.toEntity()
            countriesRepository.updateDescriptionByName(updatedEntity)
        }
    }

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

data class AddUiState(
    val countryDetails: CountryDetails = CountryDetails(),
    val isEntryValid: Boolean = false,
    val selectedCountry: CountryEntity? = null,
    val searchResults: List<CountryEntity> = emptyList(),
    val searchQuery: String = "",
    val searchActive: Boolean = false,
)

data class CountryDetails(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val updatedAt: Long = 0L,
    val rating: Int = 0
)

fun CountryDetails.toEntity(): CountryEntity =
    CountryEntity(
        id = id,
        name = name,
        description = description,
        updatedAt = updatedAt,
        rating = rating
    )

fun CountryEntity.toDetails(): CountryDetails =
    CountryDetails(
        id = id,
        name = name,
        description = description.orEmpty(),
        updatedAt = updatedAt ?: 0L,
        rating = rating ?: 0
    )

