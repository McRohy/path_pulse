package com.example.pathpulse.screens


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathpulse.data.dataMomories.CountriesRepository
import com.example.pathpulse.data.dataMomories.CountryEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class AddUiState(
    val description: String = "",
    val searchResults: List<CountryEntity> = emptyList()
)

class AddViewModel(private val countriesRepository: CountriesRepository) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    private val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState

    init {
        viewModelScope.launch {
            countriesRepository.readAll()
                .collect { allCountries ->
                    _uiState.update { it.copy(searchResults = allCountries) }
                }
        }
    }


    fun onSearchQueryChange(newQuery: String) {
        searchQuery.value = newQuery
    }

    fun onSearchQuerySubmit() {

    }

    fun onDescriptionChange(newDescription: String) {
        _uiState.value = _uiState.value.copy(description = newDescription)
    }

    fun save() {

    }
}
