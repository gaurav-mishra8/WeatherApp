package com.greenbot.weatherapp.mapper

import com.greenbot.domain.model.WeatherForecast
import com.greenbot.weatherapp.model.ForecastViewData
import com.greenbot.weatherapp.model.WeatherForecastViewData
import javax.inject.Inject

open class WeatherForecastViewMapper @Inject constructor() :
    ViewMapper<WeatherForecast, WeatherForecastViewData> {

    override fun mapToView(weatherForecast: WeatherForecast): WeatherForecastViewData {

        val forecastList = mutableListOf<ForecastViewData>()

        weatherForecast.forecastList.forEach {
            val forecastViewData = ForecastViewData(it.day, "${it.avgTemp} C")
            forecastList.add(forecastViewData)
        }

        return WeatherForecastViewData(
            weatherForecast.locationName,
            "${weatherForecast.currentTemp}\u00B0",
            forecastList
        )

    }
}