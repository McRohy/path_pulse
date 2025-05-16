package com.example.pathpulse.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
     * Companion object pre ukladanie konštánt.
     * Využitie pre vyhnutie sa „magickým číslam“.
     */
    companion object {
        // udržiavanie spojenia -> vyhnutie sa zbytočným zastaveniam a opätovným spúšťaniam
        private const val TIMEOUT_MILLIS = 5_000L
        private const val TOTAL_COUNTRIES = 195
    }

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
