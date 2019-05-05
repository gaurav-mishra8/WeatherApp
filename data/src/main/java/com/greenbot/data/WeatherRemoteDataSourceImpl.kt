package com.greenbot.data

import com.greenbot.data.model.WeatherForecastResponse
import io.reactivex.Single

class WeatherRemoteDataSourceImpl() : WeatherRemoteDataSource {

    override fun getWeatherForecast(lat: Double, lon: Double, days: Int): Single<WeatherForecastResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}