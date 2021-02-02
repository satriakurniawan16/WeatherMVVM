package com.satriakurniawan.jakartaweatherapp.view.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.satriakurniawan.jakartaweatherapp.R
import com.satriakurniawan.jakartaweatherapp.model.WeatherResponse
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

import android.provider.Settings
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.satriakurniawan.jakartaweatherapp.BuildConfig
import com.satriakurniawan.jakartaweatherapp.view.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    lateinit var viewModelWeatherDb : MainDbViewModel

    var isSaved = false

    private var mFusedLocationClient: FusedLocationProviderClient? = null

    protected var mLastLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModelWeatherDb = ViewModelProvider(this).get(MainDbViewModel::class.java)


        viewModelWeatherDb.weather.observe(this, Observer {
            it?.let {
                isSaved = !isSaved
            }
        })



        viewModelWeatherDb.status.observe(this, Observer {
            if(it)
                Toast.makeText(this, "Disimpan", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Sudah tersimpan", Toast.LENGTH_SHORT).show()
        })


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }


    public override fun onStart() {
        super.onStart()

        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDsiplay(it: WeatherResponse) {

        place_info.text = it.name
        desc_weather.text = it.weather[0].description
        temp_weather.text = (it.main.temp - 273.15).toInt().toString() +" °C"
        feels_like.text = (it.main.feels_like - 273.15).toInt().toString() +" °C"
        humidity.text = it.main.humidity.toString() + " %"
        wind.text = it.wind.speed.toString()+" km/hr"
        pressure.text = it.main.pressure.toString()+" hdpa"
        sunset.text =  getDateTime(it.sys.sunset.toString())
        sunrise.text =  getDateTime(it.sys.sunrise.toString())

        detail_button.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }

        val weatherModel = it.weather[0]
        tambahdata.setOnClickListener {
            if (isSaved) Toast.makeText(this, "telah tersimpan", Toast.LENGTH_SHORT)
                .show() else viewModelWeatherDb.insert(weatherModel)
            isSaved = !isSaved
        }
        Glide.with(this).load("http://openweathermap.org/img/wn/${it.weather[0].icon}@2x.png").into(imageTemp)
    }


    private fun getDateTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("HH:mm aa")
            val netDate = Date(s.toLong() * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationClient!!.lastLocation
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    mLastLocation = task.result

                    Log.d(
                        TAG, "getLastLocation: "+
                            (mLastLocation )!!.latitude)
                    getData( (mLastLocation )!!.latitude.toString(), (mLastLocation )!!.latitude.toString())
                } else {
                    Log.w(TAG, "getLastLocation:exception", task.exception)
                    showMessage(getString(R.string.no_location_detected))
                }
            }
    }

    private fun getData(latitude: String, longitude: String) {
        viewModel.getWeather(latitude,longitude,this)

        viewModel.loading.observe(this, Observer {
            if(it){
                loading_weather.visibility = View.VISIBLE
            }else{
                loading_weather.visibility = View.GONE
            }
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            Log.d("isinya", "onCreate: "+ it)
        })

        viewModel.weather.observe(this, Observer {
            it?.let {
                setDsiplay(it)
            }
        })
    }

    private fun showMessage(text: String) {
        val container = findViewById<View>(R.id.main_activity_container)
        if (container != null) {
            Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
        }
    }

    private fun showSnackbar(mainTextStringId: Int, actionStringId: Int,
                             listener: View.OnClickListener) {

        Toast.makeText(this@MainActivity, getString(mainTextStringId), Toast.LENGTH_LONG).show()
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this@MainActivity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
            Manifest.permission.ACCESS_COARSE_LOCATION)

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                View.OnClickListener {
                    // Request permission
                    startLocationPermissionRequest()
                })

        } else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation()
            } else {
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                    View.OnClickListener {
                        // Build intent that displays the App settings screen.
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package",
                            BuildConfig.APPLICATION_ID, null)
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    })
            }
        }
    }

    companion object {

        private val TAG = "LocationProvider"

        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }
}