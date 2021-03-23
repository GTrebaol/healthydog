package com.gtreb.healthydog.modules.evolution.presentation

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gtreb.healthydog.common.data.IDogsRepository
import com.gtreb.healthydog.common.domain.Dog
import com.gtreb.healthydog.common.domain.EvolutionData
import com.gtreb.healthydog.common.domain.Picture
import com.gtreb.healthydog.common.implementations.TimberMonitor
import com.gtreb.healthydog.common.navigation.IDispatcherService
import com.gtreb.healthydog.common.presentation.CustomViewModel
import com.gtreb.healthydog.modules.evolution.EvolutionCoordinator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class EvolutionFragmentViewModel(
    application: Application,
    private val dogsRepository: IDogsRepository,
    private val dispatcher: IDispatcherService,
    private val coordinator: EvolutionCoordinator,
    private val monitor: TimberMonitor
) : CustomViewModel(application) {

    private lateinit var dogId: String
    var dogsEvolutionData = MutableLiveData<List<EvolutionData>>()
    var dogModel = MutableLiveData<Dog>()
    var dogPicture = MutableLiveData<Picture>()
    var isLoading = MutableLiveData(true)

    fun init(dogId: String) {
        this.dogId = dogId
    }

    override fun load() {
        getDog(dogId)
    }

    private fun getDog(dogId: String) = viewModelScope.launch(dispatcher.io) {
        val dogFlow = dogsRepository.getDogs()
        val evolutionFlow = dogsRepository.getEvolutionDataForDog(dogId)
        val pictureFlow = dogsRepository.getFavoritePictureForDog(dogId)

        dogFlow.zip(evolutionFlow) { dogs: List<Dog>, list: List<EvolutionData> ->
            dogModel.postValue(dogs.first())
            dogsEvolutionData.postValue(list)
            monitor.logD(dogModel.value.toString())

        }.zip(pictureFlow) { _, picture: Picture? ->
            dogPicture.postValue(picture)
            delay(200)
            isLoading.postValue(false)
        }.single()
    }

}