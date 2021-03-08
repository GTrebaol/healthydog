package com.gtreb.healthydog.api

import android.location.Location
import com.gtreb.healthydog.BuildConfig
import com.gtreb.healthydog.api.common.quickCallApi
import com.gtreb.healthydog.common.implementation.TimberMonitor
import com.gtreb.healthydog.common.navigation.IDispatcherService
import com.gtreb.healthydog.model.VeterinaryPlace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GooglePlacesRepository(
    private val googleMapsApi: GoogleMapsApi,
    private val logger: TimberMonitor,
    private val dispatcher: IDispatcherService
) {

    fun findNearbyVets(
        location: Location,
        radius: Int
    ): Flow<List<VeterinaryPlace>?> = flow {
        val shopType = "veterinary_care"
        emit(
            googleMapsApi.findNearbyPlaces(
                location.latitude.toString().plus(",").plus(location.longitude),
                radius,
                shopType,
                BuildConfig.API_KEY,
                true
            )
                .quickCallApi(logger, ::findNearbyVets.name)?.body?.results!!.asList()
        )
    }


}