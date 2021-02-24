package com.gtreb.healthydog.exit

import com.gtreb.healthydog.AppCoordinator
import com.gtreb.healthydog.dashboard.DashboardModule
import com.gtreb.healthydog.evolution.EvolutionModule

/**
 * Exit points of [DashboardModule]
 */
class EvolutionModuleExit(
    private val coordinator: AppCoordinator
) : EvolutionModule.ModuleExit {

    override fun exitToOtherAccounts(arguments: Map<String, Any?>) {
        TODO("Not yet implemented")
    }

    override fun exitToSmi(arguments: Map<String, Any?>) {
        TODO("Not yet implemented")
    }

}