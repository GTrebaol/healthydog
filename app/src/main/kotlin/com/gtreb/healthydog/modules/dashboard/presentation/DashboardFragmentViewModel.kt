package com.gtreb.healthydog.modules.dashboard.presentation

import android.app.Application
import com.gtreb.healthydog.common.presentation.CustomViewModel
import com.gtreb.healthydog.modules.dashboard.DashboardCoordinator

class DashboardFragmentViewModel(
    private val coordinator: DashboardCoordinator,
    application: Application,
) : CustomViewModel(application) {
    override fun load() {
        TODO("Not yet implemented")
    }

}