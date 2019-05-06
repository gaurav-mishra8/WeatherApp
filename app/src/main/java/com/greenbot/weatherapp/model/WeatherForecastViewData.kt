package com.greenbot.weatherapp.model

data class WeatherForecastViewData(
    val locationName: String,
    val currentTemp: String,
    val forecastList: List<ForecastViewData>
)

data class ForecastViewData(val day: String, val avgTemp: String)