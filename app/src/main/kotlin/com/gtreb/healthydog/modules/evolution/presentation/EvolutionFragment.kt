package com.gtreb.healthydog.modules.evolution.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gtreb.healthydog.R
import com.gtreb.healthydog.common.navigation.NavigationItem
import com.gtreb.healthydog.common.presentation.CustomFragment
import com.gtreb.healthydog.databinding.EvolutionFragmentBinding
import com.gtreb.healthydog.modules.evolution.EvolutionModule
import com.gtreb.healthydog.modules.evolution.EvolutionModule.Params.DOG_ID
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.sharedViewModel

class EvolutionFragment : CustomFragment<EvolutionFragmentBinding>() {

    private val viewModelEvolution: EvolutionFragmentViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.evolution_fragment
    private lateinit var dogId: String


    override val navigationItem: NavigationItem
        get() = NavigationItem(EvolutionModule::class, EvolutionFragment::class)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dogId = arguments?.getString(DOG_ID) ?: 1.toString()
        viewModelEvolution.init(dogId)
        viewModelEvolution.load()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @ExperimentalCoroutinesApi
    override fun bindViewModels(binding: EvolutionFragmentBinding) {
        binding.viewModelEvolution = viewModelEvolution
    }
}