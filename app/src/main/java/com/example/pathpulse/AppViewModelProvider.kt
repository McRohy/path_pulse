
package com.example.pathpulse

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pathpulse.screens.AddViewModel
import com.example.pathpulse.screens.explorer.ExplorerViewModel
import com.example.pathpulse.screens.MemoriesViewModel
import com.example.pathpulse.screens.TravelStatsViewModel


/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
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
            ExplorerViewModel()
        }
    }
}

fun CreationExtras.countryApplication(): PathPulseApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as PathPulseApplication)