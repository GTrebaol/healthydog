package com.gtreb.healthydog.common.data

import com.gtreb.healthydog.common.domain.Dog
import com.gtreb.healthydog.common.domain.EvolutionData
import kotlinx.coroutines.flow.Flow

abstract class IDogsRepository {
    abstract fun getDog(id: String): Flow<Dog>
    abstract fun getEvolutionDataForDog(id: String): Flow<List<EvolutionData>>
}