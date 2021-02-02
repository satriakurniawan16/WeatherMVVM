package com.satriakurniawan.jakartaweatherapp.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "weather_table")
data class Wind(

    @SerializedName("speed")
    val speed: Double,

    @SerializedName("deg")
    val deg: Int,

    ) : Parcelable