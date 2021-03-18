package com.gtreb.healthydog.modules.evolution

import com.gtreb.healthydog.common.interfaces.IRouter
import com.gtreb.healthydog.modules.evolution.presentation.EvolutionFragment

class EvolutionCoordinator(
    private val router: IRouter,
    private val exit: EvolutionModule.ModuleExit
) {

    fun goToEvolution() {
        router.show(EvolutionFragment::class.java)
    }

    fun goToOtherAccount() = exit.exitToOtherAccounts()
}