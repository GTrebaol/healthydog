package com.gtreb.healthydog.modules.evolution

import com.gtreb.healthydog.common.interfaces.IModuleEntry
import com.gtreb.healthydog.common.interfaces.IModuleExit

object EvolutionModule {
    object Params {
        const val DOG_ID = "EvolutionModule.DogId"
    }

    interface ModuleEntry : IModuleEntry {
        fun startEvolution(arguments: Map<String, Any?> = mapOf())
    }

    interface ModuleExit : IModuleExit {
        fun exitToOtherAccounts(arguments: Map<String, Any?> = mapOf())

    }

}