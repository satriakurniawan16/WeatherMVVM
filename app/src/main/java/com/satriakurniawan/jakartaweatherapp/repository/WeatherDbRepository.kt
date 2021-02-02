package com.satriakurniawan.jakartaweatherapp.repository

import android.util.Log
import com.satriakurniawan.jakartaweatherapp.database.WeatherDao
import com.satriakurniawan.jakartaweatherapp.model.Weather

class WeatherDbRepository(private val weatherDao : WeatherDao){

    suspend fun getMoviesDb(onQuery : (List<Weather>)->Unit){
        val weather = weatherDao.getWeatherDb()
        onQuery(weather)
    }

    suspend fun insert(weather: Weather, onSuccess : (Boolean)->Unit){
        weatherDao.insert(weather).apply {
            Log.d("INSERT", "Success insert Weather")
            onSuccess(true)
        }
    }

    suspend fun getWeatherDb(id : Int, onQuery : (Weather?)->Unit){
        val weather = weatherDao.getSingleWeatherDb(id)
        onQuery(weather)
    }

    suspend fun deleteMovieDb(id: Int,onSuccess : (Boolean)->Unit ){
        weatherDao.deleteSingleMovieDb(id).apply {
            Log.d("DELETE", "Success delete Weather")
            onSuccess(false)
        }
    }
}