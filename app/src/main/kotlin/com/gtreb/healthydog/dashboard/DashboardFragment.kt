package com.gtreb.healthydog.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gtreb.healthydog.R
import com.gtreb.healthydog.common.implementation.CustomFragment
import com.gtreb.healthydog.common.navigation.NavigationItem
import org.koin.android.ext.android.inject

class DashboardFragment : CustomFragment() {

    private val appCoordinator: DashboardCoordinator by inject()
    override val navigationItem: NavigationItem
        get() = NavigationItem(DashBoardModule::class, DashboardFragment::class)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }
}