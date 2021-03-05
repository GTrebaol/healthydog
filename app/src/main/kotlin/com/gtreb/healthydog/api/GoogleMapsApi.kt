package com.gtreb.healthydog.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface GoogleMapsApi {

    @GET("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location={latitude},{longitude}&radius={radius}&type={type}&key={api_key}&sensor=true")
    fun findNearbyPlaces(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
        @Path("radius") radius: Int,
        @Path("type") shopType: String,
        @Path("api_key") apiKey: String
    ): Call<String>
}