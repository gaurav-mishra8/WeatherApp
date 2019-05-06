package com.greenbot.data.remote.service

import com.greenbot.data.model.WeatherForecastResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastService {

    @GET("forecast.json")
    fun getWeatherForecast(@Query("q") query: String, @Query("days") days: Int): Single<WeatherForecastResponse>
}