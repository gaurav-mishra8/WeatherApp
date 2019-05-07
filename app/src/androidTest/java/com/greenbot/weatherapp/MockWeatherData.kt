package com.greenbot.weatherapp

import com.greenbot.domain.model.Forecast
import com.greenbot.domain.model.WeatherForecast
import com.greenbot.weatherapp.model.ForecastViewData
import com.greenbot.weatherapp.model.WeatherForecastViewData
import java.util.*

fun randomString(): String {
    return UUID.randomUUID().toString()
}

fun randomDouble(): Double {
    return Random().nextDouble()
}

private fun buildMockForecastList(): List<Forecast> {
    val forecastList = mutableListOf<Forecast>()

    repeat(4) {
        forecastList.add(Forecast(day = randomString(), avgTemp = randomDouble()))
    }

    return forecastList

}

fun buildMockWeatherData(): WeatherForecast {
    val weatherForecast = WeatherForecast(
        locationName = randomString(),
        currentTemp = randomDouble(),
        forecastList = buildMockForecastList()
    )

    return weatherForecast
}

fun buildMockWeatherViewData(weatherForecast: WeatherForecast): WeatherForecastViewData {

    val forecastList = mutableListOf<ForecastViewData>()

    weatherForecast.forecastList.forEach {
        val forecastViewData = ForecastViewData(it.day, "${it.avgTemp} C")
        forecastList.add(forecastViewData)
    }

    val weatherForecastViewData: WeatherForecastViewData = WeatherForecastViewData(
        locationName = weatherForecast.locationName,
        currentTemp = "${weatherForecast.currentTemp.toString()}°", forecastList = forecastList
    )

    return weatherForecastViewData

}