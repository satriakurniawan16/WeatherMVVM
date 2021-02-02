package com.satriakurniawan.jakartaweatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satriakurniawan.jakartaweatherapp.R
import com.satriakurniawan.jakartaweatherapp.model.Weather
import kotlinx.android.synthetic.main.item_weather.view.*

class DataDbAdapter (private val context: Context?, private val list: List<Weather>, private val listener : (Weather)->Unit) : RecyclerView.Adapter<DataDbAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_weather,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(list[position], listener)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(weather: Weather, listener: (Weather) -> Unit){
            itemView.nameweather.text = weather.description
            Glide.with(itemView).load("http://openweathermap.org/img/wn/${weather.icon}@2x.png").into(itemView.icon_weather)

            itemView.setOnClickListener {
                listener(weather)
            }
        }
    }

}