package com.gtreb.healthydog.api.common

import com.gtreb.healthydog.common.implementations.TimberMonitor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object CustomProviders {
    private const val TIMEOUT = 60L
    private const val QUALIFIER_OKHTTP_SUFFIX = ".okhttp"


    /**
     * Provide the retrofit client
     * @param client Okhttp client
     * @param moshi parser json
     * @param url The endpoint
     * @return The retrofit client
     */
    inline fun <reified T> provideRetrofitClient(monitor: TimberMonitor, moshi: Moshi): T {
        val okHttpClient = provideClient(monitor)
        loadKoinModules(
            module {
                single(getQualifier(T::class.java)) { okHttpClient }
            }
        )
        return Retrofit.Builder()
            .baseUrl("http://localhost/")
            .client(okHttpClient.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build().create(T::class.java)
    }


    /**
     * Provide okhttp client builder
     * @param context Application context who implement ITokenExpired
     * @return okhttp client builder with timeout
     */
    fun provideClient(
        monitor: TimberMonitor
    ): OkHttpClient.Builder {


        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor(logger = CustomHttpLogger(monitor)).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
    }


    fun getQualifier(clazz: Class<*>) = qualifier(clazz.name + QUALIFIER_OKHTTP_SUFFIX)


}