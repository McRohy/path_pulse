package com.example.pathpulse

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pathpulse.screens.memories.AddViewModel
import com.example.pathpulse.screens.memories.MemoriesViewModel
import com.example.pathpulse.screens.stats.TravelStatsViewModel
import com.example.pathpulse.screens.memories.MemoryDetailViewModel


/**
 * Poskytovateľ ViewModelov pre celú aplikáciu.
 * Definuje fabriku, ktorá vytvorí inštancie všetkých potrebných ViewModelov.
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            AddViewModel(
                countryApplication().container.countriesRepository
            )
        }

        initializer {
            MemoriesViewModel(
                countryApplication().container.countriesRepository
            )
        }

        initializer {
            TravelStatsViewModel(
                countryApplication().container.countriesRepository
            )
        }

        initializer {
            MemoryDetailViewModel(
                this.createSavedStateHandle(),        // pre pracu s navArgumenty
                countryApplication().container.countriesRepository
            )
        }
    }
}

/**
 * Rozšírenie, ktoré z objektu Application získa a vráti inštanciu PathPulseApplication.
 * Používa sa vo fabrike ViewModelov na získanie repozitára z aplikácie.
 */
fun CreationExtras.countryApplication(): PathPulseApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as PathPulseApplication)