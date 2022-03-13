package com.greenbot.domain.model

data class WeatherForecast(
    val locationName: String,
    val currentTemp: Double,
    val forecastList: List<Forecast>
)

data class Forecast(val day: String, val avgTemp: Double)