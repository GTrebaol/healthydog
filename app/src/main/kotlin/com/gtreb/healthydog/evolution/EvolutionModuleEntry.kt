package com.gtreb.healthydog.evolution

import kotlinx.coroutines.ExperimentalCoroutinesApi

class EvolutionModuleEntry(
    private val coordinator: EvolutionCoordinator
) : EvolutionModule.ModuleEntry {

    @ExperimentalCoroutinesApi
    override fun startEvolution(arguments: Map<String, Any?>) {
        coordinator.goToEvolution()
    }

}