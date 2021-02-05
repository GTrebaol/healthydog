package com.gtreb.healthydog

import android.content.SharedPreferences
import com.gtreb.healthydog.common.IKoinModule
import com.gtreb.healthydog.dashboard.DashBoardModule
import com.gtreb.healthydog.exit.DashboardModuleExit
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModule : IKoinModule {

    private val navigation = module {
        single { AppCoordinator(get(), get()) }
        single { ActivityHolder(get()) }
    }

    private val exits = module {
        single<DashBoardModule.ModuleExit> { DashboardModuleExit(get()) }
        single<SharedPreferences.Editor> { get<SharedPreferences>().edit() }
    }

    override val modules: List<Module> = listOf(navigation, exits)
}