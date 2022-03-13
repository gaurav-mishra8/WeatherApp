package com.greenbot.data.model

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("location") val location: Location? = null,
    @SerializedName("current") val currentTemp: CurrentTemperature? = null,
    @SerializedName("forecast") val forecast: Forecast? = null
)

data class Location(@SerializedName("name") val locationName: String?, val country: String?)

data class CurrentTemperature(@SerializedName("temp_c") val temperature: Double?)

data class Forecast(@SerializedName("forecastday") val forecastDayList: List<ForecastDay>)

data class ForecastDay(
    @SerializedName("date") val date: String?,
    @SerializedName("day") val day: Day?
)

data class Day(@SerializedName("avgtemp_c") val avgTemp: Double?)
