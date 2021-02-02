package com.satriakurniawan.jakartaweatherapp.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.satriakurniawan.jakartaweatherapp.model.Weather

@Database(entities = [Weather::class], version = 2, exportSchema = false)
abstract class MyDatabase : RoomDatabase(){

    abstract fun weatherDao() : WeatherDao

    companion object{
        @Volatile
        private var INSTANCE : MyDatabase? = null
        fun getDatabase(context: Context) : MyDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }

}