package com.gtreb.healthydog.veterinaire

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.gtreb.healthydog.api.GooglePlacesRepository
import com.gtreb.healthydog.common.navigation.IDispatcherService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class VeterinaireFragmentViewModel(
    private val dispatcher: IDispatcherService,
    private val googleRepository: GooglePlacesRepository,
    private val context: Context,
    private val coordinator: VeterinaireCoordinator,
    application: Application,
) : AndroidViewModel(application) {

    lateinit var vetLocations: MutableLiveData<List<LatLng>>
    lateinit var latitude: MutableLiveData<Double>
    lateinit var longitude: MutableLiveData<Double>


    fun load(radius: Int) {
        //coordinator.showProgressDialogFragment(PROGRESS_SCOPE_ID)
        viewModelScope.launch(dispatcher.io) {
            val placesFlow =
                googleRepository.findNearbyVets(latitude.value!!, longitude.value!!, radius)
            placesFlow.collect {
                if (null != it) {
                    vetLocations.value = it
                }
            }
        }
    }


}