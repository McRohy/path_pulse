package com.example.pathpulse

import android.app.Application
import com.example.pathpulse.data.dataMomories.AppContainer
import com.example.pathpulse.data.dataMomories.AppDataContainer


/**
 * PathPulseApplication slúži ako vstupný bod aplikácie a stará sa o prípravu
 * všetkých potrebných závislostí pri jej spustení.
 */
class PathPulseApplication : Application() {
    /**
     * Kontajner závislostí, kde sú uložené inštancie služieb a dát.
     * Prístupný z hociktorej časti aplikácie.
     */
    lateinit var container: AppContainer

    /**
     * Inicializačná metóda volaná pri spustení aplikácie.
     * Pripraví AppDataContainer s potrebnými závislosťami.
     */
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
