package com.greenbot.weatherapp.mapper

import com.greenbot.weatherapp.model.ForecastViewData
import com.greenbot.weatherapp.view.WeatherDataFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherForecastViewMapperTest {

    private val mapper = WeatherForecastViewMapper()

    @Test
    fun mapToView() {

        val weatherForecast = WeatherDataFactory.buildMockWeatherData()

        val weatherForecastViewData = mapper.mapToView(weatherForecast)

        val forecastList = mutableListOf<ForecastViewData>()

        weatherForecast.forecastList.forEach {
            forecastList.add(ForecastViewData(it.day, "${it.avgTemp.toString()} C"))
        }

        assertEquals(weatherForecast.locationName, weatherForecastViewData.locationName)
        assertEquals("${weatherForecast.currentTemp}°", weatherForecastViewData.currentTemp)
        assertEquals(forecastList, weatherForecastViewData.forecastList)


    }

}