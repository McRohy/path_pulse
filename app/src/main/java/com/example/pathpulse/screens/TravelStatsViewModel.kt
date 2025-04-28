package com.example.pathpulse.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathpulse.data.dataMomories.CountriesRepository
import com.example.pathpulse.data.dataMomories.CountryEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * opravene podla cvika 8
 */

class TravelStatsViewModel(private val countriesRepository: CountriesRepository) : ViewModel() {

    //magicke cisla na jednom mieste
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        private const val TOTAL_COUNTRIES = 195
    }

    val uiState: StateFlow<TravelStatsUiState> = combine(
        countriesRepository.getCountriesCount(),
        countriesRepository.getLastUpdatedCountryName()
    ) { countriesCounted, lastName ->
        TravelStatsUiState(
            numberOfCountriesVisited = countriesCounted,
            lastUpdatedCountryName = lastName,
            progress = countriesCounted / TOTAL_COUNTRIES.toFloat()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = TravelStatsUiState()
    )
}

data class TravelStatsUiState(
    val numberOfCountriesVisited: Int = 0,
    val lastUpdatedCountryName: String? = null,
    val progress: Float = 0f
)
