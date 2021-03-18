package com.gtreb.healthydog.api

import com.gtreb.healthydog.api.common.CustomProviders.provideRetrofitClient
import com.gtreb.healthydog.common.interfaces.IKoinModule
import com.gtreb.healthydog.modules.veterinary.data.VeterinaryRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module

internal object KoinModuleApi : IKoinModule {

    override val modules = listOf(
        module {
            single {
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            }
            single { provideRetrofitClient<GoogleMapsApi>(get(), get()) }
            single { VeterinaryRepository(get(), get()) }
        }
    )

}