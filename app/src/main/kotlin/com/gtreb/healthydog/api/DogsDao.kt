package com.gtreb.healthydog.api

import androidx.room.*
import com.gtreb.healthydog.common.domain.Dog
import kotlinx.coroutines.flow.Flow

@Dao
interface DogsDao {

    @Query("SELECT * FROM dog WHERE dogId = :id LIMIT 1")
    fun findDogById(id: Long): Flow<Dog?>

    @Query("SELECT * FROM dog WHERE name = :fullName LIMIT 1")
    fun findDogByName(fullName: String): Flow<Dog?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(dog: Dog): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg dogs: Dog): List<Long>

    @Query("DELETE FROM dog")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(dog: Dog)

    @Query("SELECT * FROM dog ORDER BY name ASC")
    fun allDogs(): Flow<List<Dog>>
}