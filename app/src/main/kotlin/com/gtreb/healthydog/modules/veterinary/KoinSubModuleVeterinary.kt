package com.gtreb.healthydog.modules.veterinary

import com.gtreb.healthydog.common.interfaces.IKoinModule
import com.gtreb.healthydog.modules.veterinary.presentation.VeterinaryFragment
import com.gtreb.healthydog.modules.veterinary.presentation.VeterinaryFragmentViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.core.module.Module
import org.koin.dsl.module

internal object KoinSubModuleVeterinary : IKoinModule {
    private val veterinaryModule = module {
        fragment { VeterinaryFragment() }
        viewModel { VeterinaryFragmentViewModel(get(), get(), get(), get(), get()) }
        single<VeterinaryModule.ModuleEntry> { VeterinaireModuleEntry(get()) }
    }

    override val modules: List<Module> = listOf(veterinaryModule)
}