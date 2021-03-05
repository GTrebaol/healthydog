package com.gtreb.healthydog.utils

import com.gtreb.healthydog.common.implementation.TimberMonitor
import okhttp3.logging.HttpLoggingInterceptor

class MyHttpLogger(private val monitor: TimberMonitor) : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        monitor.logV(message)
    }
}
