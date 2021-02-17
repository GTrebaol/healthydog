package com.gtreb.healthydog.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gtreb.healthydog.R
import com.gtreb.healthydog.common.implementation.CustomFragment
import com.gtreb.healthydog.common.navigation.NavigationItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.java.KoinJavaComponent.inject

class DashboardFragment : CustomFragment<DashboardFragmentBinding>() {

    private val appCoordinator: DashboardCoordinator by inject()
    val viewModel: DashboardFragmentViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.fragment_dashboard

    override val navigationItem: NavigationItem
        get() = NavigationItem(DashBoardModule::class, DashboardFragment::class)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }



    @ExperimentalCoroutinesApi
    override fun bindViewModels(binding: DashBoardFragmentBinding) {
        binding.viewModel = viewModel
        viewModel.fetch()
    }
}