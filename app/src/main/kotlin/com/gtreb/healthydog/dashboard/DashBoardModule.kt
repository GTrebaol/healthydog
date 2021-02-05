package com.gtreb.healthydog.dashboard

import com.gtreb.healthydog.common.IModuleEntry
import com.gtreb.healthydog.common.IModuleExit

object DashBoardModule {
    object Params {
        const val CONTEXT = "DashBoardModule.Context"
        const val PHONE_NUMBER = "DashBoardModule.PhoneNumber"
        const val ACCOUNT_ID = "DashBoardModule.AccountId"
    }

    interface ModuleEntry : IModuleEntry {
        fun startDashBoard(arguments: Map<String, Any?> = mapOf())
    }

    interface ModuleExit : IModuleExit {


        fun exitToOtherAccounts(arguments: Map<String, Any?> = mapOf())

    }

}