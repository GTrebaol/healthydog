package com.gtreb.healthydog.modules.veterinary.domain

import com.squareup.moshi.Json

data class VeterinaryLocation(
    @field:Json(name = "latitude") val lat: Double,
    @field:Json(name = "longitude") val lng: Double,
)