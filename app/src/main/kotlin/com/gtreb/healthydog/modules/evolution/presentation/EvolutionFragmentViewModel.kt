package com.gtreb.healthydog.modules.evolution.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.gtreb.healthydog.modules.evolution.EvolutionCoordinator

class EvolutionFragmentViewModel (
    private val context: Context,
    private val coordinator: EvolutionCoordinator,
    application: Application,
) : AndroidViewModel(application) {

}