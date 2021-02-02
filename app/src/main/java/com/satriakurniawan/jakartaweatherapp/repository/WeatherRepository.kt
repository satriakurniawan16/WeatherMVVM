package com.satriakurniawan.jakartaweatherapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.satriakurniawan.jakartaweatherapp.BuildConfig
import com.satriakurniawan.jakartaweatherapp.model.WeatherResponse
import com.satriakurniawan.jakartaweatherapp.network.ApiResource

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WeatherRepository {


    var latStr = MutableLiveData<String>()
    var lonStr = MutableLiveData<String>()

    companion object {
        const val apiKey = "aae148bf4856416b5450b38913d25481"
    }

    fun getWeather(onResult: (WeatherResponse) -> Unit, onError: (Throwable) -> Unit) {
        latStr.value?.let {
            lonStr.value?.let { it1 ->
                ApiResource.create().getWeather(
                    latStr = it,
                    lonStr = it1,
                    apiKey).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<WeatherResponse> {
                        override fun onComplete() {

                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: WeatherResponse) {
                            onResult(t)
                        }

                        private fun onResult(t: WeatherResponse) {
                        }

                        override fun onError(e: Throwable) {
                            onError(e)
                        }

                    }
                    )
            }
        }
    }


}