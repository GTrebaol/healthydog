package com.gtreb.healthydog.modules.veterinary.domain

import com.squareup.moshi.Json

data class VeterinarySearchResponse(
    @field:Json(name = "results") val results: Array<VeterinaryPlace>,
    @field:Json(name = "status") val status: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VeterinarySearchResponse

        if (!results.contentEquals(other.results)) return false
        if (status != other.status) return false

        return true
    }

    override fun hashCode(): Int {
        var result = results.contentHashCode()
        result = 31 * result + status.hashCode()
        return result
    }
}