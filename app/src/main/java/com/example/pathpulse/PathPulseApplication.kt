package com.example.pathpulse

import android.app.Application
import com.example.pathpulse.data.dataMomories.AppContainer
import com.example.pathpulse.data.dataMomories.AppDataContainer


class PathPulseApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}
