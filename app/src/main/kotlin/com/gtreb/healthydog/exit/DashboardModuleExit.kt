package com.gtreb.healthydog.exit

import com.gtreb.healthydog.AppCoordinator
import com.gtreb.healthydog.dashboard.DashBoardModule

/**
 * Exit points of [DashBoardModule]
 */
class DashboardModuleExit(
    private val coordinator: AppCoordinator
) : DashBoardModule.ModuleExit {

    override fun exitToOtherAccounts(arguments: Map<String, Any?>) {
        TODO("Not yet implemented")
    }

    override fun exitToSmi(arguments: Map<String, Any?>) {
        TODO("Not yet implemented")
    }

}