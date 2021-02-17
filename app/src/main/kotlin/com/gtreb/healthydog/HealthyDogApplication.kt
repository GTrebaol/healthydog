package com.gtreb.healthydog

import android.app.Application
import com.gtreb.healthydog.dashboard.KoinSubModuleDashboard
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class HealthyDogApplication : Application() {
    companion object {
        lateinit var instance: HealthyDogApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@HealthyDogApplication)
            fragmentFactory()
            modules(KoinModule.modules)
            modules(KoinSubModuleDashboard.modules)
        }
    }
}