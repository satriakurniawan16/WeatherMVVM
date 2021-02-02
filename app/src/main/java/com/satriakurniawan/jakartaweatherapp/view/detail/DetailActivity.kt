package com.satriakurniawan.jakartaweatherapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.satriakurniawan.jakartaweatherapp.R
import com.satriakurniawan.jakartaweatherapp.adapter.DataDbAdapter
import com.satriakurniawan.jakartaweatherapp.model.Weather
import com.satriakurniawan.jakartaweatherapp.view.main.MainDbViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {


    private var listWeather: MutableList<Weather> = mutableListOf()
    private lateinit var viewModel: MainDbViewModel
    private lateinit var adapter: DataDbAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProvider(this).get(MainDbViewModel::class.java)
        adapter = DataDbAdapter(this, listWeather) {
        showDialogAlert(it.id)
        }

        rv_fav_weathers.layoutManager = LinearLayoutManager(this)
        rv_fav_weathers.adapter = adapter

        displayData()
    }

    private fun displayData() {
        viewModel.getMoviesDb()

        viewModel.loading.observe(this, Observer {
            if (it) {
                loading_fav_weathers.visibility = View.VISIBLE
            } else {
                loading_fav_weathers.visibility = View.GONE
            }
        })

        viewModel.weatherDb.observe(this, Observer {

            it?.let {
                empty_list.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                listWeather.clear()
                listWeather.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    fun showDialogAlert(id: Int){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Perhatian")
        builder.setMessage("Apakah anda yakin untuk menghapus data ini")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            viewModel.deleteMovieDb(id)
            displayData()
            Toast.makeText(applicationContext,
                "Data Telah Terhapus", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }

        builder.setNeutralButton("Maybe") { dialog, which ->
        }
        builder.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMoviesDb()
    }
}