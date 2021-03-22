package com.gtreb.healthydog.api.common

import com.gtreb.healthydog.api.AppDataBase
import com.gtreb.healthydog.api.DogsDao
import com.gtreb.healthydog.api.EvolutionDataDao
import com.gtreb.healthydog.common.domain.Dog
import com.gtreb.healthydog.common.domain.EvolutionData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


suspend fun populateDatabase(database: AppDataBase?) {
    database?.let { db ->
        withContext(Dispatchers.IO) {
            val dogsDao: DogsDao = db.DogsDao()
            val evolutionDataDao: EvolutionDataDao = db.EvolutionDataDao()

            dogsDao.deleteAll()

            val dogOne = Dog(
                name = "Lucky", race = "Berger Blanc Suisse", birthdate = Date(1603900894)
            )
            val dogTwo = Dog(name = "Enez", race = "Bauceron", birthdate = Date(1256745694))

            val dogOneId = dogsDao.insert(dogOne)
            val dogOneTwo = dogsDao.insert(dogOne)

            val evolutionDataOne = EvolutionData(
                date = Date(1611331903),
                weight = 15F,
                height = 25F,
                dogId = dogOneId
            )
            val evolutionDataTwo = EvolutionData(
                date = Date(1614010303),
                weight = 20F,
                height = 32F,
                dogId = dogOneId
            )
            val evolutionDataThree = EvolutionData(
                date = Date(1616429066),
                weight = 27F,
                height = 40F,
                dogId = dogOneId
            )

            evolutionDataDao.insert(evolutionDataOne, evolutionDataTwo, evolutionDataThree)
        }
    }
}