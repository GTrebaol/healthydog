package com.gtreb.healthydog.dashboard

import kotlinx.coroutines.ExperimentalCoroutinesApi

class DashboardModuleEntry(
    private val coordinator: DashboardCoordinator
) : DashboardModule.ModuleEntry {

    @ExperimentalCoroutinesApi
    override fun startDashBoard(arguments: Map<String, Any?>) {
        coordinator.goToDashBoard()
    }
}