package com.gtreb.healthydog.modules.veterinary.presentation

import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Intent
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.gtreb.healthydog.common.implementations.TimberMonitor
import com.gtreb.healthydog.common.navigation.IDispatcherService
import com.gtreb.healthydog.common.presentation.CustomViewModel
import com.gtreb.healthydog.modules.veterinary.data.VeterinaryRepository
import com.gtreb.healthydog.modules.veterinary.domain.VeterinaryPlace
import com.gtreb.healthydog.utils.Constants
import com.gtreb.healthydog.utils.Constants.DEFAULT_RADIUS
import com.gtreb.healthydog.utils.SettingsAction
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VeterinaryFragmentViewModel(
    private val dispatcher: IDispatcherService,
    private val googleRepository: VeterinaryRepository,
    private val logger: TimberMonitor,
    application: Application,
) : CustomViewModel(application) {

    lateinit var googleMap: GoogleMap
    lateinit var lastKnownLocation: Location
    var vetLocations = MutableLiveData<List<VeterinaryPlace>>()

    override fun load() {
        viewModelScope.launch(dispatcher.io) {
            val placesFlow = googleRepository.findNearbyVets(lastKnownLocation, DEFAULT_RADIUS)
            placesFlow.collect {
                withContext(dispatcher.main) {
                    updateGoogleMap()
                }
                vetLocations.postValue(it)
            }
        }
    }

    private fun updateGoogleMap() {
        val position = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    lastKnownLocation.latitude,
                    lastKnownLocation.longitude
                ), Constants.DEFAULT_ZOOM.toFloat()
            )
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
    }

    fun goToGpsSettings() {
        val i = Intent(SettingsAction.GPS.value)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            getApplication<Application>().applicationContext.startActivity(i)
        } catch (e: ActivityNotFoundException) {
            logger.logE(e.message.toString(), e)
        }
    }
}