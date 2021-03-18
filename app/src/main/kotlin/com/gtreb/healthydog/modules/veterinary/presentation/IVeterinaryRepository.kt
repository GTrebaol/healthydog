package com.gtreb.healthydog.modules.veterinary.presentation

import android.location.Location
import com.gtreb.healthydog.modules.veterinary.domain.VeterinaryPlace
import kotlinx.coroutines.flow.Flow

interface IVeterinaryRepository {
    fun findNearbyVets(
        location: Location,
        radius: Int
    ): Flow<List<VeterinaryPlace>?>
}