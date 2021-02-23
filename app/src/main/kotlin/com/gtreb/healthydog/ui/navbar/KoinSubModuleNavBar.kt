package com.gtreb.healthydog.ui.navbar

import com.gtreb.healthydog.common.interfaces.IKoinModule
import com.gtreb.healthydog.dashboard.DashboardFragment
import com.gtreb.healthydog.dashboard.DashboardFragmentViewModel
import org.koin.core.Koin
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.core.module.Module

object KoinSubModuleNavBar : IKoinModule {

    private val navbarModule = module {
        viewModel{ NavBarFragmentViewModel(get())}
    }


    override val modules: List<Module> = listOf(navbarModule)


}