package com.gtreb.healthydog

import com.gtreb.healthydog.common.interfaces.IRouter
import com.gtreb.healthydog.dashboard.DashBoardModule
import com.gtreb.healthydog.dashboard.DashboardFragment
import org.koin.core.KoinComponent
import org.koin.core.inject

class AppCoordinator(private val router: IRouter) : KoinComponent {

    //private val authentication: AuthenticationModule.ModuleEntry by inject()
    private val dashboard: DashBoardModule.ModuleEntry by inject()
    //private val account: AccountsOverviewModule.ModuleEntry by inject()

    internal fun startMain() {
        router.show(DashboardFragment::class.java)
    }

    internal fun startDashBoard() {
        dashboard.startDashBoard()
    }

}