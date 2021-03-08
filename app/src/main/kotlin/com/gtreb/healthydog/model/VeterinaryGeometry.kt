package com.gtreb.healthydog.model

import com.squareup.moshi.Json

data class VeterinaryGeometry(
    @field:Json(name = "location") val location: VeterinaryLocation
)
