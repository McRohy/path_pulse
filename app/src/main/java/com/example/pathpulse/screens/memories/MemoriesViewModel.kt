package com.example.pathpulse.screens.memories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathpulse.constans.TIMEOUT_MILLIS
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
