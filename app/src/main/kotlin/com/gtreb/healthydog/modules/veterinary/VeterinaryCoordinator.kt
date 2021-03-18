package com.gtreb.healthydog.modules.veterinary

import com.gtreb.healthydog.common.interfaces.IRouter
import com.gtreb.healthydog.modules.veterinary.presentation.VeterinaryFragment

class VeterinaryCoordinator(
    private val router: IRouter,
    private val exit: VeterinaryModule.ModuleExit
) {

    fun goToVeterinary() {
        router.show(VeterinaryFragment::class.java)
    }

}