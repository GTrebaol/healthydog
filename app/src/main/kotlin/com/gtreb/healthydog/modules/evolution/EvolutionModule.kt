package com.gtreb.healthydog.modules.evolution

import com.gtreb.healthydog.common.interfaces.IModuleEntry
import com.gtreb.healthydog.common.interfaces.IModuleExit

object EvolutionModule {
    object Params {
        const val CONTEXT = "EvolutionModule.Context"
        const val PHONE_NUMBER = "EvolutionModule.PhoneNumber"
        const val ACCOUNT_ID = "EvolutionModule.AccountId"
    }

    interface ModuleEntry : IModuleEntry {
        fun startEvolution(arguments: Map<String, Any?> = mapOf())
    }

    interface ModuleExit : IModuleExit {
        fun exitToOtherAccounts(arguments: Map<String, Any?> = mapOf())

    }

}