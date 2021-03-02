package com.gtreb.healthydog.exit

import com.gtreb.healthydog.AppCoordinator
import com.gtreb.healthydog.dashboard.DashboardModule
import com.gtreb.healthydog.evolution.EvolutionModule
import com.gtreb.healthydog.veterinaire.VeterinaireModule

/**
 * Exit points of [DashboardModule]
 */
class VeterinaireModuleExit(
    private val coordinator: AppCoordinator
) : VeterinaireModule.ModuleExit {

    override fun exitToOtherAccounts(arguments: Map<String, Any?>) {
        TODO("Not yet implemented")
    }

    override fun exitToSmi(arguments: Map<String, Any?>) {
        TODO("Not yet implemented")
    }

}