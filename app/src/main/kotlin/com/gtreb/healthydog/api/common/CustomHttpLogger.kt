package com.gtreb.healthydog.api.common

import com.gtreb.healthydog.common.implementations.TimberMonitor
import okhttp3.logging.HttpLoggingInterceptor

class CustomHttpLogger(private val monitor: TimberMonitor) : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        monitor.logV(message)
    }
}
