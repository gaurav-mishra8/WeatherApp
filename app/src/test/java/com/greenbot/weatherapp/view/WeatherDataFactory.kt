package com.greenbot.weatherapp.view

import com.greenbot.domain.model.Forecast
import com.greenbot.domain.model.WeatherForecast
import com.greenbot.domain.usecase.GetWeatherUseCase
import com.greenbot.weatherapp.model.ForecastViewData
import com.greenbot.weatherapp.model.WeatherForecastViewData
import java.util.*

object WeatherDataFactory {

    private fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    private fun randomDouble(): Double {
        return Random().nextDouble()
    }

    private fun randomInt(): Int {
        return Random().nextInt(5)
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

    fun getErrorString() = "Something went wrong at our end!"

    fun buildMockWeatherViewData(): WeatherForecastViewData {

        val forecastList = mutableListOf<ForecastViewData>()

        repeat(4) {
            forecastList.add(ForecastViewData(randomString(), randomString()))
        }

        return WeatherForecastViewData(
            locationName = randomString(),
            currentTemp = randomString(),
            forecastList = forecastList
        )
    }

    fun getWeatherParams(): GetWeatherUseCase.Params {
        return GetWeatherUseCase.Params(randomDouble(), randomDouble(), 3)
    }

}