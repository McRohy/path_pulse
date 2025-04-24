package com.example.pathpulse.data.dataMomories

import android.content.Context

interface AppContainer {
    val countriesRepository: CountriesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val countriesRepository: CountriesRepository by lazy {
        LocalCountriesRepository(
            AppDatabase.getDatabase(context).countryDao()
        )
    }
}
