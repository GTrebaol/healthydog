package com.gtreb.healthydog.common.domain

import androidx.room.*
import com.gtreb.healthydog.common.domain.EvolutionData.Companion.DATE
import com.gtreb.healthydog.common.domain.EvolutionData.Companion.DOG_ID
import com.gtreb.healthydog.common.domain.EvolutionData.Companion.TABLE_NAME
import java.util.*

@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = Dog::class,
        parentColumns = [Dog.DOG_ID],
        childColumns = [DOG_ID],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(DATE), Index(DOG_ID)]
)
data class EvolutionData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = EVOLUTION_ID) var id: Long = 0,
    @ColumnInfo(name = DOG_ID) var dogId: Long,
    @ColumnInfo(name = DATE) var date: Date,
    @ColumnInfo(name = HEIGHT) var height: Float,
    @ColumnInfo(name = WEIGHT) var weight: Float
) {

    companion object {
        const val TABLE_NAME = "evolution"
        const val EVOLUTION_ID = "evolutionId"
        const val HEIGHT = "height"
        const val WEIGHT = "weight"
        const val DATE = "date"
        const val DOG_ID = "dogId"
    }
}
