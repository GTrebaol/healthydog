package com.gtreb.healthydog.api

import androidx.room.*
import com.gtreb.healthydog.common.domain.EvolutionData
import kotlinx.coroutines.flow.Flow

@Dao
interface EvolutionDataDao {

    @Query("SELECT * FROM evolution WHERE dogId = :id")
    fun findEvolutionDataByDogId(id: Long): Flow<List<EvolutionData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(evolutionData: EvolutionData): Long


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg evolutionDatas: EvolutionData): List<Long>

    @Query("DELETE FROM evolution")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(evolutionData: EvolutionData)

    @Query("SELECT * FROM evolution WHERE evolutionId = :id")
    fun getEvolutionData(id: Long): Flow<EvolutionData>
}