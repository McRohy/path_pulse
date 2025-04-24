package com.example.pathpulse.screens.memories



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathpulse.data.dataMomories.CountriesRepository
import com.example.pathpulse.data.dataMomories.CountryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class AddUiState(
    val countryDetails: CountryDetails = CountryDetails(),
    val isEntryValid: Boolean = false,
    val selectedCountry: CountryEntity? = null,
    val searchResults: List<CountryEntity> = emptyList(),
    val searchQuery: String = ""
)

data class CountryDetails(
    val id: Int = 0,
    val name: String = "",
    val description: String? = null,
    val updatedAt: Long? = null
)

// mapovanie UI → DB
fun CountryDetails.toEntity(): CountryEntity =
    CountryEntity(
        id = id,
        name = name,
        description = description,
        updatedAt = updatedAt
    )
fun CountryEntity.toDetails(): CountryDetails =
    CountryDetails(
        id = id,
        name = name,
        description = description,
        updatedAt = updatedAt
    )


class AddViewModel(private val countriesRepository: CountriesRepository) : ViewModel() {
//    val searchQuery = MutableStateFlow("")

    private var _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState.asStateFlow() //read only

    init {
        viewModelScope.launch {
            uiState
                .map { it.searchQuery }
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        countriesRepository.readAll()
                    } else {
                        countriesRepository.searchCountries(query)
                    }
                }
                .collect { filtered ->
                    _uiState.update { it.copy(searchResults = filtered) }
                }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _uiState.update { it.copy(searchQuery = newQuery) }
    }

    fun onSearchQuerySubmit() {

    }

    fun onDescriptionChange(newDescription: String) {
        val updatedDetails = _uiState.value.countryDetails.copy(description = newDescription)
        _uiState.update {
            it.copy(
                countryDetails = updatedDetails,
                isEntryValid = validateInput(updatedDetails)
            )
        }
    }

    fun onCountrySelected(country: CountryEntity) {
        val details = country.toDetails()
        _uiState.update {
            it.copy(
                selectedCountry = country,
                countryDetails = details,
                isEntryValid = validateInput(details),
                searchQuery = country.name
            )
        }
//        searchQuery.value = country.name
    }

    private fun validateInput(details: CountryDetails): Boolean =
        details.name.isNotBlank() && !details.description.isNullOrBlank()

    suspend fun save() {
        val state = _uiState.value
        if (state.selectedCountry != null && state.isEntryValid) {
            val updatedEntity = state.countryDetails.toEntity()
            countriesRepository.updateDescriptionByName(updatedEntity)
        }
    }
}