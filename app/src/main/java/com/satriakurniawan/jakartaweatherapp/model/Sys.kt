package com.satriakurniawan.jakartaweatherapp.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "weather_table")
data class Sys(

    @SerializedName("sunrise")
    val sunrise : Int,

    @SerializedName("sunset")
    val sunset : Int

) : Parcelable