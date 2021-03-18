package com.gtreb.healthydog.modules.veterinary.data

import android.location.Location
import com.gtreb.healthydog.BuildConfig
import com.gtreb.healthydog.api.GoogleMapsApi
import com.gtreb.healthydog.api.common.quickCallApi
import com.gtreb.healthydog.common.implementation.TimberMonitor
import com.gtreb.healthydog.modules.veterinary.domain.VeterinaryPlace
import com.gtreb.healthydog.modules.veterinary.presentation.IVeterinaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class VeterinaryRepository(
    private val googleMapsApi: GoogleMapsApi,
    private val logger: TimberMonitor
) : IVeterinaryRepository {

    private val shopType = "veterinary_care"

    override fun findNearbyVets(
        location: Location,
        radius: Int
    ): Flow<List<VeterinaryPlace>?> = flow {
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