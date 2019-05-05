package com.greenbot.domain.repository

import com.greenbot.domain.model.WeatherForecast
import io.reactivex.Single

/**
 * Interface to fetch weather related information from the inner data layer
 */
interface WeatherRepository {

    /**
     * Gets weather info for a given location coordinates and number of days
     */
    fun getWeatherForecast(lat: Double, lon: Double, days: Int): Single<WeatherForecast>

}