package com.satriakurniawan.jakartaweatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("main")
    val main : Main,
    @SerializedName("wind")
    val wind : Wind,
    @SerializedName("sys")
    val sys : Sys,
    @SerializedName("dt")
    val dt : Int? = null,
    @SerializedName("name")
    val name : String? = null,
)