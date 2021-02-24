package com.gtreb.healthydog.evolution

import com.gtreb.healthydog.common.interfaces.IKoinModule
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