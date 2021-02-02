package com.satriakurniawan.jakartaweatherapp.view.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.satriakurniawan.jakartaweatherapp.R
import com.satriakurniawan.jakartaweatherapp.model.*
import com.satriakurniawan.jakartaweatherapp.repository.WeatherRepository

class MainViewModel : ViewModel() {

    private val repository = WeatherRepository()
    val weather: MutableLiveData<WeatherResponse> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    fun getWeather(lat: String,lon: String,context: Context?) {
        loading.value = true
        repository.latStr.value = lat
        repository.lonStr.value = lon
        repository.getWeather({
            weather.value = it
            loading.value = false
        }, {
            context?.let {
                error.value = it.getString(R.string.fail_load)
            }
            loading.value = false
        })
    }

}