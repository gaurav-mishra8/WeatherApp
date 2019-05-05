package com.greenbot.data

import com.greenbot.data.mapper.WeatherForecastDataMapper
import com.greenbot.domain.model.WeatherForecast
import com.greenbot.domain.repository.WeatherRepository
import io.reactivex.Single

class WeatherDataSource(
    private val mapper: WeatherForecastDataMapper,
    private val remoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {

    override fun getWeatherForecast(lat: Double, lon: Double, days: Int): Single<WeatherForecast> {
        return remoteDataSource.getWeatherForecast(lat, lon, days).map {
            mapper.mapFromDataModel(it)
        }
    }
}