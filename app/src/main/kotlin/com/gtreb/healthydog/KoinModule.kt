package com.gtreb.healthydog

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.gtreb.healthydog.common.implementation.Router
import com.gtreb.healthydog.common.implementation.TimberMonitor
import com.gtreb.healthydog.common.interfaces.IKoinModule
import com.gtreb.healthydog.common.interfaces.IRouter
import com.gtreb.healthydog.common.navigation.DefaultDispatcherService
import com.gtreb.healthydog.common.navigation.IDispatcherService
import com.gtreb.healthydog.common.navigation.NavigationViewData
import com.gtreb.healthydog.dashboard.DashboardModule
import com.gtreb.healthydog.dashboard.DashboardCoordinator
import com.gtreb.healthydog.dashboard.KoinSubModuleDashboard
import com.gtreb.healthydog.exit.DashboardModuleExit
import com.gtreb.healthydog.ui.logic.HealthyDogActivityViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModule : IKoinModule {

    // File name for shared preferences
    private const val SHARED_PREFERENCES_FILE_NAME = "gtreb_shared_preferences"

    private val routerModule = module {
        single<IRouter> { Router() }
    }

    /** Single instance of shared preference encrypted */
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
        single<SharedPreferences.Editor> { get<SharedPreferences>().edit() }
    }

    private val monitorModule = module {
        single{
            TimberMonitor()
        }
    }

    private val dashboardCoordinatorModule = module {
        single { DashboardCoordinator(get(), get()) }
    }

    private val dispatcherModule = module {
            single<IDispatcherService>(IDispatcherService.Default) { DefaultDispatcherService() }
            single<IDispatcherService> { get(IDispatcherService.Default) }
    }



    override val modules: List<Module> = listOf(monitorModule, routerModule, sharedPreferencesModule, navigationModule, exitModule, dashboardCoordinatorModule, dispatcherModule)
        .plus(KoinSubModuleDashboard.modules)
}