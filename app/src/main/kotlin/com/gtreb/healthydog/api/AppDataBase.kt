package com.gtreb.healthydog.api

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gtreb.healthydog.api.common.DateConverter
import com.gtreb.healthydog.api.common.populateDatabase
import com.gtreb.healthydog.common.domain.Dog
import com.gtreb.healthydog.common.domain.EvolutionData
import com.gtreb.healthydog.common.domain.Picture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Dog::class, EvolutionData::class, Picture::class], version = 2)
@TypeConverters(DateConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun DogsDao(): DogsDao
    abstract fun EvolutionDataDao(): EvolutionDataDao
    abstract fun PictureDao(): PictureDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null
        private const val DATABASE_NAME = "healthy-dog.db"
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDataBase::class.java, DATABASE_NAME
        )
            .build()

        fun getDatabase(context: Context): AppDataBase {
            if (instance == null) {
                synchronized(AppDataBase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDataBase::class.java,
                            DATABASE_NAME
                        )
                            .addCallback(object : Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    GlobalScope.launch(Dispatchers.IO) { populateDatabase(instance) }
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            //Let's open the database
            instance?.isOpen
            return instance!!
        }

    }
}