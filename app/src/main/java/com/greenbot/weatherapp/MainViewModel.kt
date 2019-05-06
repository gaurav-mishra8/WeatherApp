package com.greenbot.weatherapp

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.greenbot.domain.model.WeatherForecast
import com.greenbot.domain.usecase.GetWeatherUseCase
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MainViewModel @Inject constructor(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {

    private val weatherForecastLiveData = MutableLiveData<Resource<WeatherForecast>>()
    private val disposable = SingleDisposableObserver()

    fun getWeatherForecast(): LiveData<Resource<WeatherForecast>> = weatherForecastLiveData

    fun fetchWeatherForecastDetails(latitude: Double, longitude: Double) {

        weatherForecastLiveData.value = Resource(status = ResourceStatus.LOADING)

        getWeatherUseCase.execute(disposable, GetWeatherUseCase.Params(latitude, longitude, 4))

    }

    override fun onCleared() {
        super.onCleared()
        getWeatherUseCase.clearDisposables()
    }

    inner class SingleDisposableObserver() : DisposableSingleObserver<WeatherForecast>() {
        override fun onSuccess(value: WeatherForecast) {
            weatherForecastLiveData.postValue(Resource(status = ResourceStatus.SUCCESS, data = value))
        }

        override fun onError(e: Throwable?) {
            weatherForecastLiveData.postValue(Resource(status = ResourceStatus.ERROR, error = "Something went wrong"))
        }
    }

}