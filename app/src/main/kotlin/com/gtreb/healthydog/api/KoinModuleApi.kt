package com.gtreb.healthydog.api

import com.gtreb.healthydog.api.common.CustomProviders.provideRetrofitClient
import com.gtreb.healthydog.common.data.DogsRepository
import com.gtreb.healthydog.common.data.IDogsRepository
import com.gtreb.healthydog.common.interfaces.IKoinModule
import com.gtreb.healthydog.modules.veterinary.data.VeterinaryRepository
import com.gtreb.healthydog.modules.veterinary.presentation.IVeterinaryRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module

internal object KoinModuleApi : IKoinModule {

    override val modules = listOf(
        module {
            single {
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            }
            single { AppDataBase.getDatabase(get()) }
            single { provideRetrofitClient<GoogleMapsApi>(get(), get()) }
            single { get<AppDataBase>().DogsDao() }
            single { get<AppDataBase>().EvolutionDataDao() }
            single { get<AppDataBase>().PictureDao() }
            single<IDogsRepository> { DogsRepository(get(), get(), get(), get()) }
            single<IVeterinaryRepository> { VeterinaryRepository(get(), get()) }
        }
    )

}