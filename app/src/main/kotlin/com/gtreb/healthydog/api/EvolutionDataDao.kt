package com.gtreb.healthydog.api

import androidx.room.*
import com.gtreb.healthydog.common.domain.EvolutionData

@Dao
interface EvolutionDataDao {

    @Query("SELECT * FROM evolution WHERE dogId = :id")
    suspend fun findEvolutionDataByDogId(id: Long): List<EvolutionData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(evolutionData: EvolutionData): Long


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg evolutionDatas: EvolutionData)


    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(evolutionData: EvolutionData)

    @Query("SELECT * FROM evolution WHERE evolutionId = :id")
    fun getEvolutionData(id: Long): EvolutionData
}