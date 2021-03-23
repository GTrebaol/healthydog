package com.gtreb.healthydog.api

import androidx.room.*
import com.gtreb.healthydog.common.domain.Picture
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDao {
    @Query("SELECT * FROM picture WHERE dogId = :id")
    fun findPicturesByDogId(id: Long): Flow<List<Picture>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(picture: Picture): Long


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg pictures: Picture): List<Long>

    @Query("DELETE FROM picture")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(picture: Picture)

    @Query("SELECT * FROM picture WHERE pictureId = :id")
    fun getPicture(id: Long): Flow<Picture?>

    @Query("SELECT * FROM picture WHERE dogId = :dogId AND isFavorite = 1")
    fun findPicturesFavoritePictureForDogId(dogId: String): Flow<Picture?>
}