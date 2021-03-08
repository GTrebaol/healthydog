package com.gtreb.healthydog.api.common

import com.gtreb.healthydog.common.implementation.TimberMonitor

fun <T : Any> ApiResult<T>.handle(
    monitor: TimberMonitor,
    errorToLog: String
): ApiResult.Success<T>? = when (this) {
    is ApiResult.Error -> null.also { monitor.logE(formatForLog(errorToLog)) }
    is ApiResult.Success -> this
}
