package com.example.pathpulse.screens.memories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathpulse.data.dataMomories.CountriesRepository
import com.example.pathpulse.data.dataMomories.CountryEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MemoriesViewModel(private val countriesRepository: CountriesRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(MemoriesUiState())
    val uiState: StateFlow<MemoriesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            countriesRepository.getDescribedCountries()
                .collectLatest { countries ->
                    _uiState.update { it.copy(countriesList = countries) }
                }
        }
    }

    fun selectCountry(country: CountryEntity?) {
        _uiState.update { it.copy(selectedCountry = country) }
    }
}
data class MemoriesUiState(
    val countriesList: List<CountryEntity> = emptyList(),
    val selectedCountry: CountryEntity? = null,
)