package com.gtreb.healthydog.modules.evolution.presentation

import com.gtreb.healthydog.R
import com.gtreb.healthydog.common.navigation.NavigationItem
import com.gtreb.healthydog.common.presentation.CustomFragment
import com.gtreb.healthydog.databinding.EvolutionFragmentBinding
import com.gtreb.healthydog.modules.evolution.EvolutionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.sharedViewModel

class EvolutionFragment : CustomFragment<EvolutionFragmentBinding>() {

    private val viewModelEvolution: EvolutionFragmentViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.evolution_fragment

    override val navigationItem: NavigationItem
        get() = NavigationItem(EvolutionModule::class, EvolutionFragment::class)

    @ExperimentalCoroutinesApi
    override fun bindViewModels(binding: EvolutionFragmentBinding) {
        binding.viewModelEvolution = viewModelEvolution
    }
}