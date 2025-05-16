package com.example.pathpulse.screens.memories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathpulse.data.dataMomories.CountriesRepository
import com.example.pathpulse.data.dataMomories.CountryEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel zodpovedný za získavanie a sprístupňovanie zoznamu krajín,
 * ktoré obsahujú záznamy (spomienky).
 */
class MemoriesViewModel(countriesRepository: CountriesRepository) : ViewModel() {
    /**
     * Companion object pre ukladanie konštánt.
     */
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    /**
     * Prúd (StateFlow) zoznamu entít CountryEntity, ktoré obsahujú záznamy.
     */
    val countriesList: StateFlow<List<CountryEntity>> =
        countriesRepository.getDescribedCountries()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = emptyList()
            )
}
