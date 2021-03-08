package com.gtreb.healthydog.veterinaire

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.GoogleMap
import com.gtreb.healthydog.api.GooglePlacesRepository
import com.gtreb.healthydog.common.implementation.TimberMonitor
import com.gtreb.healthydog.common.navigation.IDispatcherService
import com.gtreb.healthydog.model.VeterinaryPlace
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class VeterinaireFragmentViewModel(
    private val dispatcher: IDispatcherService,
    private val googleRepository: GooglePlacesRepository,
    private val logger: TimberMonitor,
    private val coordinator: VeterinaireCoordinator,
    application: Application,
) : AndroidViewModel(application) {

    lateinit var googleMap: GoogleMap
    var vetLocations = MutableLiveData<List<VeterinaryPlace>>()

    fun load(location: Location, radius: Int) {
        //coordinator.showProgressDialogFragment(PROGRESS_SCOPE_ID)
        viewModelScope.launch(dispatcher.io) {
            val placesFlow =
                googleRepository.findNearbyVets(location, radius)
            placesFlow.collect {
                if (null != it) {
                    logger.logD(it.toString())
                    vetLocations.postValue(it)
                }
            }
        }
    }


}