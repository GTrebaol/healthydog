package com.gtreb.healthydog.common.data

import com.gtreb.healthydog.api.DogsDao
import com.gtreb.healthydog.api.EvolutionDataDao
import com.gtreb.healthydog.api.PictureDao
import com.gtreb.healthydog.common.domain.Dog
import com.gtreb.healthydog.common.domain.EvolutionData
import com.gtreb.healthydog.common.domain.Picture
import com.gtreb.healthydog.common.implementations.TimberMonitor
import kotlinx.coroutines.flow.Flow

class DogsRepository(
    private val dogsDao: DogsDao,
    private val evolutionDataDao: EvolutionDataDao,
    private val pictureDao: PictureDao,
    private val logger: TimberMonitor
) : IDogsRepository() {

    override fun getDogs(): Flow<List<Dog>> {
        return dogsDao.allDogs()
    }

    override fun getDog(id: String): Flow<Dog?> {
        logger.logD(::getDog.name)
        return dogsDao.findDogById(id.toLong())
    }

    override fun getEvolutionDataForDog(id: String): Flow<List<EvolutionData>> {
        logger.logD(::getEvolutionDataForDog.name)
        return evolutionDataDao.findEvolutionDataByDogId(id.toLong())
    }

    override fun getPicturesForDog(id: String): Flow<List<Picture>> {
        logger.logD(::getPicturesForDog.name)
        return pictureDao.findPicturesByDogId(id.toLong())
    }

    override fun getFavoritePictureForDog(dogId: String): Flow<Picture?> {
        return pictureDao.findPicturesFavoritePictureForDogId(dogId)
    }
}