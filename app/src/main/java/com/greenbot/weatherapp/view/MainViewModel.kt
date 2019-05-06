package com.greenbot.weatherapp.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.greenbot.domain.model.WeatherForecast
import com.greenbot.domain.usecase.GetWeatherUseCase
import com.greenbot.weatherapp.Resource
import com.greenbot.weatherapp.ResourceStatus
import com.greenbot.weatherapp.mapper.WeatherForecastViewMapper
import com.greenbot.weatherapp.model.WeatherForecastViewData
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val mapper: WeatherForecastViewMapper
) : ViewModel() {

    private val weatherForecastLiveData = MutableLiveData<Resource<WeatherForecastViewData>>()
    private val disposable = SingleDisposableObserver()

    fun getWeatherForecast(): LiveData<Resource<WeatherForecastViewData>> = weatherForecastLiveData

    fun fetchWeatherForecastDetails(latitude: Double, longitude: Double) {

        weatherForecastLiveData.value =
            Resource(status = ResourceStatus.LOADING)

        getWeatherUseCase.execute(disposable, GetWeatherUseCase.Params(latitude, longitude, 4))

    }

    override fun onCleared() {
        super.onCleared()
        getWeatherUseCase.clearDisposables()
    }

    inner class SingleDisposableObserver() : DisposableSingleObserver<WeatherForecast>() {
        override fun onSuccess(value: WeatherForecast) {
            weatherForecastLiveData.postValue(
                Resource(
                    status = ResourceStatus.SUCCESS,
                    data = mapper.mapToView(value)
                )
            )
        }

        override fun onError(e: Throwable?) {
            weatherForecastLiveData.postValue(
                Resource(
                    status = ResourceStatus.ERROR,
                    error = "Something went wrong at our end!"
                )
            )
        }
    }

}