package com.gtreb.healthydog.api

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gtreb.healthydog.common.domain.Dog

@Dao
interface DogsDao {

    @Query("SELECT * FROM dog WHERE dogId = :id LIMIT 1")
    suspend fun findDogById(id: Long): Dog?

    @Query("SELECT * FROM dog WHERE name = :fullName LIMIT 1")
    suspend fun findDogByName(fullName: String?): Dog?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dog: Dog): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg dogs: Dog)

    @Query("DELETE FROM dog")
    suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(director: Dog)

    @get:Query("SELECT * FROM dog ORDER BY name ASC")
    val allDogs: LiveData<List<Dog>>
}