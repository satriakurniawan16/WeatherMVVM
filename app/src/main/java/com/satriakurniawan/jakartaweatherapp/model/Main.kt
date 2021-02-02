package com.satriakurniawan.jakartaweatherapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "weather_table")
data class Main(

    @SerializedName("temp")
    val temp: Double,

    @SerializedName("feels_like")
    val feels_like: Double,

    @SerializedName("pressure")
    val pressure: Double,

    @SerializedName("humidity")
    val humidity: Double,

) : Parcelable