package com.gtreb.healthydog.api

import com.google.android.gms.maps.model.LatLng
import com.gtreb.healthydog.BuildConfig
import com.gtreb.healthydog.common.implementation.TimberMonitor
import com.gtreb.healthydog.utils.callApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GooglePlacesRepository(
    private val googleMapsApi: GoogleMapsApi,
    private val logger: TimberMonitor
) {

    fun findNearbyVets(
        latitude: Double,
        longitude: Double,
        radius: Int
    ): Flow<List<LatLng>?> = flow {
        val shopType = "veterinary_care"
        var loc: List<LatLng> = emptyList()
        googleMapsApi.findNearbyPlaces(latitude, longitude, radius, shopType, BuildConfig.API_KEY)
            .callApi({

            }, {
                loc = parseResponse(it.body)
            })
        emit(loc)
    }

    private fun parseResponse(body: String?): List<LatLng> {
        logger.logD(body!!)
        return emptyList()
    }


}