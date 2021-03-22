package com.gtreb.healthydog.modules.veterinary

import kotlinx.coroutines.ExperimentalCoroutinesApi

class VeterinaryModuleEntry(
    private val coordinator: VeterinaryCoordinator
) : VeterinaryModule.ModuleEntry {

    @ExperimentalCoroutinesApi
    override fun startVeterinary(arguments: Map<String, Any?>) {
        coordinator.goToVeterinary()
    }

}