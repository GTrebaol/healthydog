package com.gtreb.healthydog.veterinaire

import kotlinx.coroutines.ExperimentalCoroutinesApi

class VeterinaireModuleEntry(
    private val coordinator: VeterinaryCoordinator
) : VeterinaryModule.ModuleEntry {

    @ExperimentalCoroutinesApi
    override fun startVeterinary(arguments: Map<String, Any?>) {
        coordinator.goToVeterinary()
    }

}