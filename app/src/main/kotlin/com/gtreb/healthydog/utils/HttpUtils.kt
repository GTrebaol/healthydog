package com.gtreb.healthydog.utils

import android.content.Context
import com.gtreb.healthydog.common.implementation.TimberMonitor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


object HttpUtils {
    private const val TIMEOUT = 60L

    /**
     * Provide the retrofit client
     * @param client Okhttp client
     * @param moshi parser json
     * @param url The endpoint
     * @return The retrofit client
     */
    fun provideRetrofitClient(client: OkHttpClient, moshi: Moshi, url: String) =
        Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()


    /**
     * Provide okhttp client builder
     * @param context Application context who implement ITokenExpired
     * @return okhttp client builder with timeout
     */
    fun provideClient(
        context: Context,
        mocked: Boolean? = false,
        monitor: TimberMonitor
    ): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor(logger = MyHttpLogger(monitor)).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )


        return builder
    }

}