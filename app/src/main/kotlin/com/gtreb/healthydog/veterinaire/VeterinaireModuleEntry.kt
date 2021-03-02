package com.gtreb.healthydog.veterinaire

import kotlinx.coroutines.ExperimentalCoroutinesApi

class VeterinaireModuleEntry(
    private val coordinator: VeterinaireCoordinator
) : VeterinaireModule.ModuleEntry {

    @ExperimentalCoroutinesApi
    override fun startVeterinaire(arguments: Map<String, Any?>) {
        coordinator.goToVeterinaire()
    }

}