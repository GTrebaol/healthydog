package com.gtreb.healthydog

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.gtreb.healthydog.api.KoinModuleApi
import com.gtreb.healthydog.common.implementations.Router
import com.gtreb.healthydog.common.implementations.TimberMonitor
import com.gtreb.healthydog.common.interfaces.IKoinModule
import com.gtreb.healthydog.common.interfaces.IRouter
import com.gtreb.healthydog.common.navigation.DefaultDispatcherService
import com.gtreb.healthydog.common.navigation.IDispatcherService
import com.gtreb.healthydog.common.navigation.NavigationViewData
import com.gtreb.healthydog.common.navigation.logic.HealthyDogActivityViewModel
import com.gtreb.healthydog.modules.dashboard.DashboardCoordinator
import com.gtreb.healthydog.modules.dashboard.DashboardKoinSubModule
import com.gtreb.healthydog.modules.evolution.EvolutionCoordinator
import com.gtreb.healthydog.modules.evolution.KoinSubModuleEvolution
import com.gtreb.healthydog.modules.veterinary.VeterinaryCoordinator
import com.gtreb.healthydog.modules.veterinary.VeterinaryKoinSubModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModule : IKoinModule {

    private const val SHARED_PREFERENCES_FILE_NAME = "gtreb_shared_preferences"

    private val routerModule = module {
        single<IRouter> { Router(get()) }
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
        coordinatorsModule,
        dispatcherModule
    )
        .plus(KoinModuleApi.modules)
        .plus(DashboardKoinSubModule.modules)
        .plus(KoinSubModuleEvolution.modules)
        .plus(VeterinaryKoinSubModule.modules)
}