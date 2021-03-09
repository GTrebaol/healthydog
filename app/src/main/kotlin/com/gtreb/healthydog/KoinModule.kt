package com.gtreb.healthydog

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.gtreb.healthydog.api.KoinModuleApi
import com.gtreb.healthydog.common.implementation.Router
import com.gtreb.healthydog.common.implementation.TimberMonitor
import com.gtreb.healthydog.common.interfaces.IKoinModule
import com.gtreb.healthydog.common.interfaces.IRouter
import com.gtreb.healthydog.common.navigation.DefaultDispatcherService
import com.gtreb.healthydog.common.navigation.IDispatcherService
import com.gtreb.healthydog.common.navigation.NavigationViewData
import com.gtreb.healthydog.dashboard.DashboardCoordinator
import com.gtreb.healthydog.dashboard.DashboardModule
import com.gtreb.healthydog.dashboard.KoinSubModuleDashboard
import com.gtreb.healthydog.evolution.EvolutionCoordinator
import com.gtreb.healthydog.evolution.EvolutionModule
import com.gtreb.healthydog.evolution.KoinSubModuleEvolution
import com.gtreb.healthydog.exit.DashboardModuleExit
import com.gtreb.healthydog.exit.EvolutionModuleExit
import com.gtreb.healthydog.exit.VeterinaireModuleExit
import com.gtreb.healthydog.ui.logic.HealthyDogActivityViewModel
import com.gtreb.healthydog.veterinaire.KoinSubModuleVeterinary
import com.gtreb.healthydog.veterinaire.VeterinaryCoordinator
import com.gtreb.healthydog.veterinaire.VeterinaryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModule : IKoinModule {

    private const val SHARED_PREFERENCES_FILE_NAME = "gtreb_shared_preferences"

    private val routerModule = module {
        single<IRouter> { Router() }
    }

    private val sharedPreferencesModule = module {
        factory {
            EncryptedSharedPreferences.create(
                SHARED_PREFERENCES_FILE_NAME,
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                androidContext(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }

    private val navigationModule = module {
        single { AppCoordinator(get(), get()) }
        viewModel { HealthyDogActivityViewModel(get(), get(), get(), get()) }
        single { NavigationViewData(get())}
    }

    private val exitModule = module {
        single<DashboardModule.ModuleExit> { DashboardModuleExit(get()) }
        single<EvolutionModule.ModuleExit> { EvolutionModuleExit(get()) }
        single<VeterinaryModule.ModuleExit> { VeterinaireModuleExit(get()) }
        single<SharedPreferences.Editor> { get<SharedPreferences>().edit() }
    }

    private val monitorModule = module {
        single {
            TimberMonitor()
        }
    }

    private val coordinatorsModule = module {
        single { DashboardCoordinator(get(), get()) }
        single { EvolutionCoordinator(get(), get()) }
        single { VeterinaryCoordinator(get(), get()) }
    }

    private val dispatcherModule = module {
        single<IDispatcherService>(IDispatcherService.Default) { DefaultDispatcherService() }
        single<IDispatcherService> { get(IDispatcherService.Default) }
    }

    override val modules: List<Module> = listOf(
        monitorModule,
        routerModule,
        sharedPreferencesModule,
        navigationModule,
        exitModule,
        coordinatorsModule,
        dispatcherModule
    )
        .plus(KoinModuleApi.modules)
        .plus(KoinSubModuleDashboard.modules)
        .plus(KoinSubModuleEvolution.modules)
        .plus(KoinSubModuleVeterinary.modules)
}