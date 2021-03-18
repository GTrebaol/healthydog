package com.gtreb.healthydog.api

import com.gtreb.healthydog.modules.veterinary.domain.VeterinarySearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface GoogleMapsApi {

    @GET("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
    fun findNearbyPlaces(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String,
        @Query("key") key: String,
        @Query("sensor") sensor: Boolean
    ): Call<VeterinarySearchResponse>
}