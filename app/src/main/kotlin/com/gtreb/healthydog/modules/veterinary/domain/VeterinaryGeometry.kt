package com.gtreb.healthydog.modules.veterinary.domain

import com.squareup.moshi.Json

data class VeterinaryGeometry(
    @field:Json(name = "location") val location: VeterinaryLocation
)
