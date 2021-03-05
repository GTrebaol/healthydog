package com.gtreb.healthydog.utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import okhttp3.Headers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Extension for the class [Call] that can handle a request with successCallback and failureCallback
 * @param onFailureCallback function called when request is failed. Take in parameter an [ApiResult.Error] object.
 * @param onSuccessCallback function called when request is succeed. Take in parameter an [ApiResult.Success] object.
 */
fun <T : Any> Call<T>.callApi(
    onFailureCallback: (error: ApiResult.Error) -> Unit,
    onSuccessCallback: (success: ApiResult.Success<T>) -> Unit
) {
    this.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            onFailureCallback(
                ApiResult.Error(
                    ApiError.Internal(
                        Headers.Builder().build(),
                        "Internal error occurred. Maybe there is no internet connection.",
                        t
                    )
                )
            )
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                onSuccessCallback(
                    ApiResult.Success(
                        response.headers(),
                        response.body(),
                        response.code()
                    )
                )
                // response is automatically closed
            } else {
                onFailureCallback(
                    ApiResult.Error(
                        ApiError.Failure(
                            response.headers(),
                            response.errorBody()?.string(),
                            response.message(),
                            response.code()
                        )
                    )
                )
                response.closeFailureResponse()
            }
        }
    })
}

private fun Response<*>.closeFailureResponse() = errorBody()?.close()

/**
 * Extension for the class [Call] that can handle a request and return a coroutine.flow
 * @return coroutine.flow with an [ApiResult.Error] object or an [ApiResult.Success] object.
 */
fun <T : Any> Call<T>.flowApi() = flow<ApiResult<T>> {
    // without buffer, in a environment with only one thread, the call can offer() before the receive().
    // effectively missing some outputs.
    val result: Channel<ApiResult<T>> = Channel(Channel.BUFFERED)

    this@flowApi.callApi(
        { result.offer(it) },
        { result.offer(it) }
    )
    emit(result.receive())
}

/**
 * Extension for the class [Call] that can handle a request and return a flow of only success.
 * This extension function can be use with flow’s catch function.
 *
 * @return A [kotlinx.coroutines.flow.Flow] of [ApiResult.Success] object.
 * @throws ApiThrowable when [ApiResult.Error] is received
 * @see kotlinx.coroutines.flow.catch
 */
fun <T : Any> Call<T>.flowApiThrowable() = flow<ApiResult.Success<T>> {
    // without buffer, in a environment with only one thread, the call can offer() before the receive().
    // effectively missing some outputs.
    val result: Channel<ApiResult<T>> = Channel(Channel.BUFFERED)
    this@flowApiThrowable.callApi(
        { result.offer(it) },
        { result.offer(it) }
    )
    when (val it = result.receive()) {
        is ApiResult.Success<T> -> emit(it)
        is ApiResult.Error -> throw it.error.asThrowable()
    }
}
