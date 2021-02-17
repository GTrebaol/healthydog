package com.gtreb.healthydog.dashboard

import com.gtreb.healthydog.common.interfaces.IKoinModule
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.core.module.Module
import org.koin.dsl.module

internal object KoinSubModuleDashboard : IKoinModule {
    private val dashboardModule = module {
        fragment { DashboardFragment() }
        viewModel { DashboardFragmentViewModel(get(), get(), get()) }
    }


    override val modules: List<Module> = listOf(dashboardModule)

}