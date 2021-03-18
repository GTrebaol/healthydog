package com.gtreb.healthydog.modules.veterinary.domain

import com.squareup.moshi.Json

data class VeterinaryPlace(
    @field:Json(name = "geometry") val geometry: VeterinaryGeometry,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "vicinity") val vicinity: String,
    @field:Json(name = "user_ratings_total") val user_ratings_total: Double
)