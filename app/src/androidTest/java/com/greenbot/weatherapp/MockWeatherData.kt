package com.greenbot.weatherapp

import com.greenbot.domain.model.Forecast
import com.greenbot.domain.model.WeatherForecast
import com.greenbot.weatherapp.model.ForecastViewData
import com.greenbot.weatherapp.model.WeatherForecastViewData
import java.util.*
import java.util.concurrent.ThreadLocalRandom

fun randomUuid(): String {
    return UUID.randomUUID().toString()
}

fun randomInt(): Int {
    return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
}

fun randomLong(): Long {
    return randomInt().toLong()
}

fun randomDouble(): Double {
    return Random().nextDouble()
}

fun randomBoolean(): Boolean {
    return Math.random() < 0.5
}

private fun buildMockForecastList(): List<Forecast> {
    val forecastList = mutableListOf<Forecast>()

    repeat(4) {
        forecastList.add(Forecast(day = randomUuid(), avgTemp = randomDouble()))
    }

    return forecastList

}

fun buildMockWeatherData(): WeatherForecast {
    val weatherForecast = WeatherForecast(
        locationName = randomUuid(),
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