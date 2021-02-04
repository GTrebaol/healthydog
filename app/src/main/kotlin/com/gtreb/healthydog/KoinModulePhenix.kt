package com.gtreb.healthydog

import android.content.SharedPreferences
import com.gtreb.healthydog.common.IKoinModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

@ExperimentalCoroutinesApi
object KoinModulePhenix : IKoinModule {

    private val navigation = module {
        single { AppCoordinator(get(), get()) }
        single { ActivityHolder(get()) }
    }

    private val exits = module {
        single<AccountsOverviewModule.ModuleExit> { AccountsOverviewModuleExit(get()) }
        single<AuthenticationModule.ModuleExit> { AuthenticationModuleExit(get()) }
        single<CardOverviewModule.ModuleExit> { CardOverviewModuleExit(get()) }
        single<DashBoardModule.ModuleExit> { DashboardModuleExit(get()) }
        single<VirtualisModule.ModuleExit> { VirtualisModuleExit(get()) }
        single<MenuModule.ModuleExit> { MenuModuleExit(get(), get()) }
        single<SmiModule.ModuleExit> { SmiModuleExit(get()) }
        single<BudjetModule.ModuleExit> { BudjetModuleExit(get()) }
        single<SavingOverviewModule.ModuleExit> { SavingOverviewModuleExit(get()) }
        single<TransferModule.ModuleExit> { TransferModuleExit(get()) }
        single<SharedPreferences.Editor> { get<SharedPreferences>().edit() }
    }

    override val modules: List<Module> = listOf(navigation, exits)
}