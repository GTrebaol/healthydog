package com.gtreb.healthydog.common.data

import com.gtreb.healthydog.common.domain.Dog
import com.gtreb.healthydog.common.domain.EvolutionData
import com.gtreb.healthydog.common.domain.Picture
import kotlinx.coroutines.flow.Flow

abstract class IDogsRepository {
    abstract fun getDogs(): Flow<List<Dog>>
    abstract fun getDog(id: String): Flow<Dog?>
    abstract fun getEvolutionDataForDog(id: String): Flow<List<EvolutionData>>
    abstract fun getPicturesForDog(id: String): Flow<List<Picture>>
    abstract fun getFavoritePictureForDog(dogId: String): Flow<Picture?>
}