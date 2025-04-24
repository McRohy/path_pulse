package com.example.pathpulse.screens

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

class TravelStatsViewModel(private val countriesRepository: CountriesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(TravelStatsUiState())
    val uiState: StateFlow<TravelStatsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            countriesRepository.getDescribedCountries()
                .collectLatest { countries ->
                    _uiState.update { it.copy(countries = countries) }
                }
        }

        viewModelScope.launch {
            countriesRepository.getLastUpdatedCountryName()
                .collectLatest { lastName ->
                    _uiState.update { it.copy(lastUpdatedCountryName = lastName) }
                }
        }
    }
}

data class TravelStatsUiState(
    val countries: List<CountryEntity> = emptyList(),
    val totalCountriesInWorld: Int = 195,
    val lastUpdatedCountryName: String? = null
)
