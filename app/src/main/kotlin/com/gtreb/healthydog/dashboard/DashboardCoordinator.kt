package com.gtreb.healthydog.dashboard

import com.gtreb.healthydog.common.interfaces.IRouter

class DashboardCoordinator(
    private val router: IRouter,
    private val exit: DashBoardModule.ModuleExit
) {

    fun goToDashBoard() {
        router.show(DashboardFragment::class.java)
    }

    fun goToOtherAccount() = exit.exitToOtherAccounts()
}