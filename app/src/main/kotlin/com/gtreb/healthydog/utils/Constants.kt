package com.gtreb.healthydog.utils

object Constants {

    const val MIN_TIME_BTW_UPDATES: Long = 1000 * 60 * 1; // 1 minute
    const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Float = 10F; // 10 meters
    const val REQUEST_CODE_ACCESS_FINE_LOCATION = 50
    const val REQUEST_CODE_ACCESS_COARSE_LOCATION = 51
    const val DEFAULT_ZOOM = 14
    const val DEFAULT_RADIUS = 1500 // meters
    const val KEY_LOCATION = "KEY_LOCATION"
}