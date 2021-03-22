package com.gtreb.healthydog.common.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.gtreb.healthydog.common.domain.Dog.Companion.NAME
import com.gtreb.healthydog.common.domain.Dog.Companion.TABLE_NAME
import java.util.*

@Entity(
    tableName = TABLE_NAME,
    indices = [Index(value = [NAME], unique = true)]
)
data class Dog(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DOG_ID) var id: Long = 0,
    @ColumnInfo(name = NAME) var name: String,
    @ColumnInfo(name = BIRTHDATE) var birthdate: Date,
    @ColumnInfo(name = RACE) var race: String
) {

    companion object {
        const val TABLE_NAME = "dog"
        const val DOG_ID = "dogId"
        const val BIRTHDATE = "birthdate"
        const val RACE = "race"
        const val NAME = "name"
    }
}
