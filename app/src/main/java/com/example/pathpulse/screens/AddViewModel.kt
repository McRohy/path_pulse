package com.example.pathpulse.screens


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathpulse.data.dataMomories.CountriesRepository
import com.example.pathpulse.data.dataMomories.CountryEntity
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
    val description: String = "",
    val searchResults: List<CountryEntity> = emptyList()
)

class AddViewModel(private val countriesRepository: CountriesRepository) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    private val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            countriesRepository.readAll()
                .collect { allCountries ->
                    _uiState.update { it.copy(searchResults = allCountries) }
                }
        }
        viewModelScope.launch {
            searchQuery
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
        searchQuery.value = newQuery
    }

    fun onSearchQuerySubmit() {

    }

    fun onDescriptionChange(newDescription: String) {
        _uiState.update { it.copy(description = newDescription) }
    }


    fun save() {

    }
}