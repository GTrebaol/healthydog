package com.gtreb.healthydog.modules.evolution.presentation

import android.app.Application
import android.content.Context
import com.gtreb.healthydog.common.presentation.CustomViewModel
import com.gtreb.healthydog.modules.evolution.EvolutionCoordinator

class EvolutionFragmentViewModel(
    private val context: Context,
    private val coordinator: EvolutionCoordinator,
    application: Application,
) : CustomViewModel(application) {
    override fun load() {
        TODO("Not yet implemented")
    }

}