package com.gtreb.healthydog.dashboard

import com.gtreb.healthydog.R
import com.gtreb.healthydog.common.implementation.CustomFragment
import com.gtreb.healthydog.common.navigation.NavigationItem
import com.gtreb.healthydog.databinding.DashboardFragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DashboardFragment : CustomFragment<DashboardFragmentBinding>() {

    private val appCoordinator: DashboardCoordinator by inject()
    private val viewModelDashBoard: DashboardFragmentViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.dashboard_fragment

    override val navigationItem: NavigationItem
        get() = NavigationItem(DashboardModule::class, DashboardFragment::class)


    @ExperimentalCoroutinesApi
    override fun bindViewModels(binding: DashboardFragmentBinding) {
        binding.viewModelDashBoard = viewModelDashBoard
    }
}