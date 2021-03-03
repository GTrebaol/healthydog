package com.gtreb.healthydog.veterinaire

import com.gtreb.healthydog.common.interfaces.IKoinModule
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.core.module.Module
import org.koin.dsl.module

internal object KoinSubModuleVeterinaire : IKoinModule {
    private val veterinaireModule = module {
        fragment { VeterinaireFragment() }
        viewModel { VeterinaireFragmentViewModel(get(), get(), get()) }
        single<VeterinaireModule.ModuleEntry> { VeterinaireModuleEntry(get()) }
    }

    override val modules: List<Module> = listOf(veterinaireModule)
}