package com.gtreb.healthydog.utils


import com.gtreb.healthydog.utils.ApiResult.Error
import com.gtreb.healthydog.utils.ApiResult.Success
import okhttp3.Headers

/**
 * Sealed class for ApiResult
 * @property Success data class created when a request succeed. It contains the headers, a body<T> and a http status code.
 * @property Error data class created when a request failed. It contains an [ApiError] object.
 */
sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val headers: Headers, val body: T?, val code: Int?) :
        ApiResult<T>()

    data class Error(val error: ApiError) : ApiResult<Nothing>() {
        /**
         * Return a string that contains all information, formatted to be used in a log
         */
        fun formatForLog(functionName: String) = when (error) {
            is ApiError.Failure -> "$functionName() \t ${error.code} \t ${error.message} \t ${error.body}"
            is ApiError.Internal -> "$functionName() \t ${error.cause?.message?.let { "${error.message} \t $it" } ?: error.message}"
        }
    }

    /** Utility function to cast success as [ApiResult.Success] */
    fun asSuccess() = this as? Success<T>

    /** Utility function to cast error as [ApiResult.Error] */
    fun asError() = this as? Error
}
