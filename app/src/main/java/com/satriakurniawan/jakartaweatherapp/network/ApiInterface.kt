package com.satriakurniawan.jakartaweatherapp.network


import com.satriakurniawan.jakartaweatherapp.model.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    fun getWeather(
        @Query("lat") latStr: String,
        @Query("lon") lonStr: String,
        @Query("appid") appid: String
    )
    : Observable<WeatherResponse>

}