package com.gtreb.healthydog.utils

object Constants {
    const val EVOLUTION_PREFERENCES_KEY = "evolution_preferences_key"
    const val MIN_TIME_BTW_UPDATES: Long = 1000 * 60 * 1 // 1 minute
    const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Float = 10F // 10 meters
    const val REQUEST_CODE_ACCESS_FINE_LOCATION = 50
    const val REQUEST_CODE_ACCESS_COARSE_LOCATION = 51
    const val DEFAULT_ZOOM = 14
    const val DEFAULT_RADIUS = 1500 // meters
    const val KEY_LOCATION = "KEY_LOCATION"
    const val KEY_LATITUDE = "KEY_LATITUDE"
    const val KEY_LONGITUDE = "KEY_LONGITUDE"
    const val SIZE_ICON = 80


    const val CHART_CIRCLE_RADIUS: Float = 4f
    const val CHART_LINE_WIDTH: Float = 3f
}