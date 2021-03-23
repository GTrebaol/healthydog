package com.gtreb.healthydog.common.domain

import androidx.room.*
import com.gtreb.healthydog.common.domain.Picture.Companion.DOG_ID
import com.gtreb.healthydog.common.domain.Picture.Companion.TABLE_NAME


@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = Dog::class,
        parentColumns = [Dog.DOG_ID],
        childColumns = [DOG_ID],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(Picture.PICTURE_ID), Index(DOG_ID)]
)
data class Picture(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PICTURE_ID) var id: Long = 0,
    @ColumnInfo(name = DOG_ID) var dogId: Long,
    @ColumnInfo(name = PATH) var path: String,
    @ColumnInfo(name = IS_FAVORITE) var isFavorite: Boolean
) {

    companion object {
        const val TABLE_NAME = "picture"
        const val PICTURE_ID = "pictureId"
        const val PATH = "path"
        const val DOG_ID = "dogId"
        const val IS_FAVORITE = "isFavorite"
    }
}
