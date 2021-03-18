package com.gtreb.healthydog.modules.dashboard

import com.gtreb.healthydog.common.interfaces.IRouter
import com.gtreb.healthydog.modules.dashboard.presentation.DashboardFragment

class DashboardCoordinator(
    private val router: IRouter,
    private val exit: DashboardModule.ModuleExit
) {

    fun goToDashBoard() {
        router.show(DashboardFragment::class.java)
    }

    fun goToOtherAccount() = exit.exitToOtherAccounts()
}