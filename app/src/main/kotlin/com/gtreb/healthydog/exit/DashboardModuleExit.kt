package com.gtreb.healthydog.exit

import com.gtreb.healthydog.AppCoordinator
import com.gtreb.healthydog.dashboard.DashboardModule

/**
 * Exit points of [DashboardModule]
 */
class DashboardModuleExit(
    private val coordinator: AppCoordinator
) : DashboardModule.ModuleExit {

    override fun exitToOtherAccounts(arguments: Map<String, Any?>) {
        TODO("Not yet implemented")
    }

    override fun exitToSmi(arguments: Map<String, Any?>) {
        TODO("Not yet implemented")
    }

}