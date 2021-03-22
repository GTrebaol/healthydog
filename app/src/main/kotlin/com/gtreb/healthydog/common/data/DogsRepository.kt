package com.gtreb.healthydog.common.data

import com.gtreb.healthydog.api.DogsDao
import com.gtreb.healthydog.api.EvolutionDataDao
import com.gtreb.healthydog.common.domain.Dog
import com.gtreb.healthydog.common.domain.EvolutionData
import com.gtreb.healthydog.common.implementations.TimberMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DogsRepository(
    private val dogsDao: DogsDao,
    private val evolutionDataDao: EvolutionDataDao,
    private val logger: TimberMonitor
) : IDogsRepository() {

    override fun getDog(id: String): Flow<Dog> = flow {
        logger.logD(::getDog.name)
        dogsDao.findDogById(id.toLong())?.let { emit(it) }
    }

    override fun getEvolutionDataForDog(id: String): Flow<List<EvolutionData>> = flow {
        logger.logD(::getEvolutionDataForDog.name)
        evolutionDataDao.findEvolutionDataByDogId(id.toLong()).let { emit(it) }
    }
}