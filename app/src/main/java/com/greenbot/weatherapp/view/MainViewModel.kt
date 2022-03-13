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

    companion object {
        const val ACTION_RETRY = "retry"
        const val ACTION_LOCATION_PERMISSION_DECLINED = "location_permission_declined"
        const val DEFAULT_DAY_COUNT = 3
    }

    private val weatherForecastLiveData = MutableLiveData<Resource<WeatherForecastViewData>>()
    private val commandLiveData = MutableLiveData<Command>()

    init {
        weatherForecastLiveData.value = Resource(status = ResourceStatus.LOADING)
    }

    fun getWeatherForecast(): LiveData<Resource<WeatherForecastViewData>> = weatherForecastLiveData

    fun getCommand(): LiveData<Command> = commandLiveData

    fun fetchWeatherForecastDetails(latitude: Double, longitude: Double) {
        weatherForecastLiveData.value =
            Resource(status = ResourceStatus.LOADING)
        getWeatherUseCase.execute(
            SingleDisposableObserver(),
            GetWeatherUseCase.Params(latitude, longitude, DEFAULT_DAY_COUNT)
        )
    }

    override fun onCleared() {
        super.onCleared()
        getWeatherUseCase.clearDisposables()
    }

    fun onUserEvent(action: String) {

        when (action) {
            ACTION_RETRY -> {
                weatherForecastLiveData.value = Resource(status = ResourceStatus.LOADING)
                commandLiveData.value = Command.FetchLocation
            }
            ACTION_LOCATION_PERMISSION_DECLINED -> {
                weatherForecastLiveData.value =
                    Resource(status = ResourceStatus.ERROR, error = "Location Permission needed")
            }
        }

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

sealed class Command {
    object FetchLocation : Command()
}