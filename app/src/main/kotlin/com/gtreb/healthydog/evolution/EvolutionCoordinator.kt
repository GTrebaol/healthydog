package com.gtreb.healthydog.evolution

import com.gtreb.healthydog.common.interfaces.IRouter

class EvolutionCoordinator(
    private val router: IRouter,
    private val exit: EvolutionModule.ModuleExit
) {

    fun goToEvolution() {
        router.show(EvolutionFragment::class.java)
    }

    fun goToOtherAccount() = exit.exitToOtherAccounts()
}