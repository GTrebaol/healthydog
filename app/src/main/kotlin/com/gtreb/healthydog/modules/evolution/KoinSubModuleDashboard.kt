package com.gtreb.healthydog.modules.evolution

import com.gtreb.healthydog.common.interfaces.IKoinModule
import com.gtreb.healthydog.modules.evolution.presentation.EvolutionFragment
import com.gtreb.healthydog.modules.evolution.presentation.EvolutionFragmentViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.core.module.Module
import org.koin.dsl.module

internal object KoinSubModuleEvolution : IKoinModule {
    private val evolutionModule = module {
        fragment { EvolutionFragment() }
        viewModel { EvolutionFragmentViewModel(get(), get(), get()) }
        single<EvolutionModule.ModuleEntry> { EvolutionModuleEntry(get()) }
    }

    override val modules: List<Module> = listOf(evolutionModule)

}