package com.satriakurniawan.jakartaweatherapp.database

import androidx.room.*
import com.satriakurniawan.jakartaweatherapp.model.Weather

@Dao
interface WeatherDao {
    @Query("SELECT * from weather_table")
    suspend fun getWeatherDb() : List<Weather>

    @Query("SELECT * from weather_table WHERE id = :id")
    suspend fun getSingleWeatherDb(id : Int) : Weather?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weather: Weather)

    @Query("DELETE from weather_table WHERE id = :id")
    suspend fun deleteSingleMovieDb(id : Int)

}