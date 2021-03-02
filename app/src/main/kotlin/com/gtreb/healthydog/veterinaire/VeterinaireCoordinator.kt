package com.gtreb.healthydog.veterinaire

import com.gtreb.healthydog.common.interfaces.IRouter

class VeterinaireCoordinator(
    private val router: IRouter,
    private val exit: VeterinaireModule.ModuleExit
) {

    fun goToVeterinaire() {
        router.show(VeterinaireFragment::class.java)
    }

    fun goToOtherAccount() = exit.exitToOtherAccounts()
}