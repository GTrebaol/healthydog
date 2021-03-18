package com.gtreb.healthydog

import android.content.Context
import com.gtreb.healthydog.common.interfaces.IRouter
import com.gtreb.healthydog.modules.dashboard.DashboardModule
import com.gtreb.healthydog.modules.evolution.EvolutionModule
import com.gtreb.healthydog.modules.veterinary.VeterinaryModule
import com.gtreb.healthydog.utils.toast
import org.koin.core.KoinComponent
import org.koin.core.inject

class AppCoordinator(private val context: Context, private val router: IRouter) : KoinComponent {

    private val dashboard: DashboardModule.ModuleEntry by inject()
    private val evolution: EvolutionModule.ModuleEntry by inject()
    private val veterinaire: VeterinaryModule.ModuleEntry by inject()

    internal fun startMain() {
       goToDashboard()
    }

    internal fun goToDashboard() = dashboard.startDashBoard()
    internal fun goToEvolution() = evolution.startEvolution()
    internal fun goToCalendar() = unimplemented()
    internal fun goToBarf() = unimplemented()
    internal fun goToEmergencyVet() = veterinaire.startVeterinary()

    private fun unimplemented() {
        context.toast(
            context.resources.getString(R.string.soon_available))
    }

}