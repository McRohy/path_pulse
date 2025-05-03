package com.example.pathpulse.screens.memories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathpulse.data.dataMomories.CountriesRepository
import com.example.pathpulse.data.dataMomories.CountryEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn


class MemoriesViewModel(private val countriesRepository: CountriesRepository) : ViewModel() {

    private val _selectedCountry = MutableStateFlow<CountryEntity?>(null)
    val selectedCountry: StateFlow<CountryEntity?> = _selectedCountry.asStateFlow()

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val countriesList: StateFlow<List<CountryEntity>> =
        countriesRepository.getDescribedCountries()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = emptyList()
            )

    fun selectCountry(country: CountryEntity?) {
        _selectedCountry.value = country
    }
}
