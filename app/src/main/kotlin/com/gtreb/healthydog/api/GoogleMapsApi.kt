package com.gtreb.healthydog.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface GoogleMapsApi {

    @GET("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input={input}&radius={radius}&type={type}&key={api_key}")
    fun findNearbyPlaces(
        @Path("input") latitude : String,
        @Path("radius") longitude : Int,
        @Path("type") shopType : String,
        @Path("api_key") apiKey : String) : Call<GooglePlaceResponse>

}