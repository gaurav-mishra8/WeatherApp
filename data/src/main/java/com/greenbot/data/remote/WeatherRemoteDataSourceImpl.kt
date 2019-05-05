package com.greenbot.data.remote

import com.greenbot.data.model.WeatherForecastResponse
import com.greenbot.data.remote.WeatherRemoteDataSource
import com.greenbot.data.remote.service.WeatherForecastService
import io.reactivex.Single
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(private val weatherForecastService: WeatherForecastService) :
    WeatherRemoteDataSource {

    override fun getWeatherForecast(lat: Double, lon: Double, days: Int): Single<WeatherForecastResponse> {
        return weatherForecastService.getWeatherForecast("$lat,$lon", days)
    }
}