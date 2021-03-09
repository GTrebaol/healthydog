package com.gtreb.healthydog.veterinary

import com.gtreb.healthydog.common.interfaces.IRouter

class VeterinaryCoordinator(
    private val router: IRouter,
    private val exit: VeterinaryModule.ModuleExit
) {

    fun goToVeterinary() {
        router.show(VeterinaryFragment::class.java)
    }

}