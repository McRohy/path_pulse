package com.example.pathpulse.data.dataMomories

import android.content.Context

/**
 * Definuje kontajner závislostí pre aplikáciu PathPulse.
 * Obsahuje referenciu na repozitár
 */
interface AppContainer {
    /**
     * Repozitár pre prácu s krajinami.
     */
    val countriesRepository: CountriesRepository
}

/**
 * Implementácia AppContainer, ktorá poskytuje konkrétne inštancie
 * závislostí pomocou lokálnych dát a služby databázy.
 *
 * @param context Kontext aplikácie potrebný na prístup k databáze.
 */
class AppDataContainer(private val context: Context) : AppContainer {
    override val countriesRepository: CountriesRepository by lazy {
        /**
         * Repozitár krajín, ktorý načítava údaje z lokálnej databázy.
         */
        LocalCountriesRepository(
            AppDatabase.getDatabase(context).countryDao()
        )
    }
}
