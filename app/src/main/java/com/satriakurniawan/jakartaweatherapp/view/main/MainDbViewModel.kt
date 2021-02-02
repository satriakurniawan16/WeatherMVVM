package com.satriakurniawan.jakartaweatherapp.view.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.satriakurniawan.jakartaweatherapp.database.MyDatabase
import com.satriakurniawan.jakartaweatherapp.model.Weather
import com.satriakurniawan.jakartaweatherapp.repository.WeatherDbRepository
import kotlinx.coroutines.launch

class MainDbViewModel(application: Application) : AndroidViewModel(application){

    private val repository: WeatherDbRepository by lazy {
        val weatherDao = MyDatabase.getDatabase(application).weatherDao()
        WeatherDbRepository(weatherDao)
    }

    var status : MutableLiveData<Boolean> = MutableLiveData()
    var weatherDb : MutableLiveData<List<Weather>> = MutableLiveData()
    var weather : MutableLiveData<Weather?> = MutableLiveData()
    var loading : MutableLiveData<Boolean> = MutableLiveData()
    init {

    }
    fun insert(weather: Weather) = viewModelScope.launch{
        repository.insert(weather){
            status.value = it
        }
    }

    fun getMoviesDb() = viewModelScope.launch{
        loading.value = true
        repository.getMoviesDb {
            weatherDb.value = it
            loading.value = false
        }
    }

    fun getMovieDb(id : Int) = viewModelScope.launch {
        repository.getWeatherDb(id){
            weather.value = it
        }
    }

    fun deleteMovieDb(id:Int) = viewModelScope.launch {
        repository.deleteMovieDb(id){
            status.value = it
        }
    }
}