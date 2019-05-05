package com.greenbot.data

import com.greenbot.data.model.WeatherForecastResponse
import io.reactivex.Single

interface WeatherRemoteDataSource {

    fun getWeatherForecast(lat: Double, lon: Double, days: Int): Single<WeatherForecastResponse>

}