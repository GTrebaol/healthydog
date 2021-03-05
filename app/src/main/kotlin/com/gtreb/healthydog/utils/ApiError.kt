package com.gtreb.healthydog.utils


import okhttp3.Headers

/**
 * Class used by calls extensions for failed requests
 * @param message The message of what failed
 */
sealed class ApiError(val message: String? = null) {
    /**
     * When an internal error occurred not linked to a api failure
     * @param message The message of what failed
     * @param cause The cause of the error
     **/
    class Internal(val headers: Headers?, message: String? = null, val cause: Throwable? = null) :
        ApiError(message)

    /**
     * When an api error occurred (not returning 2xx code)
     * @param message The message of what failed
     * @param cause The cause of the error
     **/
    class Failure(
        val headers: Headers?,
        val body: String? = null,
        message: String? = null,
        val code: Int
    ) : ApiError(message)

    /** Transform this error to Throwable */
    fun asThrowable() = Throwable()

    /** Utility function to cast error as [ApiError.Internal] */
    fun asInternal() = this as? Internal

    /** Utility function to cast error as [ApiError.Failure] */
    fun asFailure() = this as? Failure
}
