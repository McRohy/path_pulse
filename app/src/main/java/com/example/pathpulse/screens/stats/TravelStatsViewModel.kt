package com.example.pathpulse.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathpulse.constans.TIMEOUT_MILLIS
import com.example.pathpulse.constans.TOTAL_COUNTRIES
import com.example.pathpulse.data.dataMomories.CountriesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel zodpovedný za zhromažďovanie štatistík cestovania
 */
class TravelStatsViewModel(countriesRepository: CountriesRepository) : ViewModel() {
    /**
     * Uchováva stav obrazovky (UI).
     * Zoznam položiek sa načíta z CountriesRepository a namapuje na TravelStatsUiState.
     */
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

/**
 * Dátová trieda predstavujúca stav UI pre štatistiky cestovania.
 */
data class TravelStatsUiState(
    val numberOfCountriesVisited: Int = 0,
    val lastUpdatedCountryName: String? = null,
    val progress: Float = 0f
)
