package com.gtreb.healthydog.modules.evolution.presentation

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gtreb.healthydog.common.data.IDogsRepository
import com.gtreb.healthydog.common.domain.Dog
import com.gtreb.healthydog.common.domain.EvolutionData
import com.gtreb.healthydog.common.implementations.TimberMonitor
import com.gtreb.healthydog.common.navigation.IDispatcherService
import com.gtreb.healthydog.common.presentation.CustomViewModel
import com.gtreb.healthydog.modules.evolution.EvolutionCoordinator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch

class EvolutionFragmentViewModel(
    application: Application,
    private val dogsRepository: IDogsRepository,
    private val dispatcher: IDispatcherService,
    private val coordinator: EvolutionCoordinator,
    private val monitor: TimberMonitor
) : CustomViewModel(application) {

    private var dogsEvolutionData = MutableLiveData<List<EvolutionData>>()
    private lateinit var dogId: String
    private lateinit var dogModel: Dog

    fun init(dogId: String) {
        this.dogId = dogId
    }

    override fun load() {
        getDog(dogId)
    }

    private fun getDog(dogId: String) = viewModelScope.launch(dispatcher.io) {
        dogModel = dogsRepository.getDog(dogId).singleOrNull() ?: run {
            viewModelScope.launch(dispatcher.main) { coordinator.goToSMI() }
            return@launch
        }
        monitor.logD(dogModel.toString())
        getDogData()
    }

    private fun getDogData() {
        viewModelScope.launch(dispatcher.io) {
            dogsRepository.getEvolutionDataForDog(dogId).collect { it ->
                dogsEvolutionData.postValue(it)
                it.forEach { detail ->
                    monitor.logD(detail.toString())
                }
            }
        }
    }


}