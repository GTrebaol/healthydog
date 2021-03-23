package com.gtreb.healthydog.modules.veterinary.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gtreb.healthydog.R
import com.gtreb.healthydog.common.implementations.TimberMonitor
import com.gtreb.healthydog.common.navigation.IDispatcherService
import com.gtreb.healthydog.common.presentation.CustomViewModel
import com.gtreb.healthydog.modules.veterinary.domain.VeterinaryPlace
import com.gtreb.healthydog.utils.Constants
import com.gtreb.healthydog.utils.Constants.DEFAULT_RADIUS
import com.gtreb.healthydog.utils.bitmapDescriptorFromVector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VeterinaryFragmentViewModel(
    private val dispatcher: IDispatcherService,
    private val googleRepository: IVeterinaryRepository,
    private val logger: TimberMonitor,
    application: Application,
) : CustomViewModel(application),
    LocationListener {

    lateinit var googleMap: GoogleMap
    private lateinit var lastKnownLocation: Location
    var goToSettings = MutableLiveData(true)
    var gpsVisibility = MutableLiveData<Int>()
    var mapAndListVisibility = MutableLiveData<Int>()
    private var locationManager: LocationManager? = null

    private val defaultLocation = LatLng(48.3987363, -4.4164396)
    var locationPermissionGranted = false
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

    override fun onLocationChanged(location: Location) {
        lastKnownLocation = location
        updatePosition()
    }

    fun startLocationManager() {
        locationManager =
            getApplication<Application>().applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    fun goToSettings() {
        goToSettings.postValue(goToSettings.value?.not())
    }

    fun addMarkerForVet(veterinaryPlace: VeterinaryPlace) {
        val position =
            LatLng(veterinaryPlace.geometry.location.lat, veterinaryPlace.geometry.location.lng)
        googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .title(veterinaryPlace.name)
                .snippet(veterinaryPlace.vicinity)
                .icon(
                    bitmapDescriptorFromVector(
                        getApplication<Application>().applicationContext,
                        R.drawable.ic_veterinarian
                    )
                )
        )
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        if (locationPermissionGranted) {
            val locationResult =
                LocationServices.getFusedLocationProviderClient(getApplication<Application>().applicationContext).lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Set the map's camera position to the current location of the device.
                    if (task.result != null) {
                        lastKnownLocation = task.result
                        updatePosition()
                    } else {
                        logger.logD("ASKING FOR LOCATION UPDATE")
                        locationManager!!.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0L,
                            0f,
                            this
                        )
                    }
                } else {
                    logger.logE("Current location is null. Using defaults.")
                    logger.logE("Exception: %s", task.exception)
                    googleMap.moveCamera(
                        CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, Constants.DEFAULT_ZOOM.toFloat())
                    )
                    googleMap.uiSettings?.isMyLocationButtonEnabled = false
                }
            }
        }

    }

    private fun updatePosition() {
        val position = LatLng(
            lastKnownLocation.latitude,
            lastKnownLocation.longitude
        )
        load()
        googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .title("Your position")
                .icon(
                    bitmapDescriptorFromVector(
                        getApplication<Application>().applicationContext,
                        R.drawable.ic_nerd
                    )
                )
        )
    }


    override fun onProviderDisabled(provider: String) {
        updateVisibility()
    }

    override fun onProviderEnabled(provider: String) {
        updateVisibility()
    }


    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        updateVisibility()
    }


    fun updateVisibility() {
        if( null != locationManager) {
            val providerEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            gpsVisibility.value = if (providerEnabled) View.VISIBLE else View.GONE
            mapAndListVisibility.value = if (!providerEnabled) View.GONE else View.VISIBLE
        }
    }

}