package com.gtreb.android.healthydog

import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple class to test calls.
 *
 * @param onCall A lambda that must return a [Response]
 * */
class DummyCall<T>(
    val onCall: () -> Response<T>
) : Call<T> {

    companion object {
        private const val EMPTY_MESSAGE = ""
        private const val EMPTY_BODY = ""
        private const val EXCEPTION_MESSAGE = "This is a test mock, you cannot use this method"

        /** Simulate a success call */
        fun <T> success(code: Int = 200, provider: () -> T): Call<T> = DummyCall<T> {
            Response.success(code, provider())
        }

        /** Simulate a failure call from API */
        fun <T> failure(
            code: Int = 400,
            message: String = EMPTY_MESSAGE,
            body: String = EMPTY_BODY
        ): Call<T> = DummyCall<T> {
            val error = Response.error<T>(code, ResponseBody.create(null, body))
            if (message != EMPTY_MESSAGE) {
                val rawResponseField = Response::class.java.getDeclaredField("rawResponse")
                rawResponseField.isAccessible = true

                val messageField = okhttp3.Response::class.java.getDeclaredField("message")
                messageField.isAccessible = true

                val rawResponse = rawResponseField.get(error) as okhttp3.Response

                messageField.set(rawResponse, message)
            }
            error
        }

        /** Simulate a failure call from API */
        fun <T> error(exception: Throwable = RuntimeException()): Call<T> = DummyCall {
            throw exception
        }
    }

    /** If the [onCall] Lambda throw an exception the callback will be onFailure */
    override fun enqueue(callback: Callback<T>) {
        try {
            val result = execute()
            callback.onResponse(this@DummyCall, result)
        } catch (e: Throwable) {
            callback.onFailure(this@DummyCall, e)
        }
    }

    override fun execute(): Response<T> = onCall()

    override fun isExecuted(): Boolean = throw NotImplementedError(EXCEPTION_MESSAGE)
    override fun clone(): Call<T> = throw NotImplementedError(EXCEPTION_MESSAGE)
    override fun isCanceled(): Boolean = throw NotImplementedError(EXCEPTION_MESSAGE)
    override fun cancel() = throw NotImplementedError(EXCEPTION_MESSAGE)
    override fun request(): Request = throw NotImplementedError(EXCEPTION_MESSAGE)
}
