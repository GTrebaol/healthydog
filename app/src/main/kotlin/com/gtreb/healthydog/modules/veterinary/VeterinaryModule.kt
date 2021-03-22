package com.gtreb.healthydog.modules.veterinary

import com.gtreb.healthydog.common.interfaces.IModuleEntry
import com.gtreb.healthydog.common.interfaces.IModuleExit

object VeterinaryModule {

    interface ModuleEntry : IModuleEntry {
        fun startVeterinary(arguments: Map<String, Any?> = mapOf())
    }

    interface ModuleExit : IModuleExit {
        fun exitToOtherAccounts(arguments: Map<String, Any?> = mapOf())

    }

}