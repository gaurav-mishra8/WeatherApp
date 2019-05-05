package com.greenbot.data.mapper

import com.greenbot.data.model.WeatherForecastResponse
import com.greenbot.domain.model.Forecast
import com.greenbot.domain.model.WeatherForecast
import java.text.SimpleDateFormat
import java.util.*

class WeatherForecastDataMapper() : DataModelMapper<WeatherForecastResponse, WeatherForecast> {

    override fun mapFromDataModel(dataModel: WeatherForecastResponse): WeatherForecast {
        val avgForecastList = mutableListOf<Forecast>()

        dataModel.forecast!!.forecastDayList.forEach {
            val dayOfWeek = getDayFromDate(it.date!!)
            val avgTemp = it.day!!.avgTemp!!
            avgForecastList.add(Forecast(dayOfWeek, avgTemp))
        }

        return WeatherForecast(
            locationName = dataModel.location!!.locationName!!,
            currentTemp = dataModel.currentTemp!!.temperature!!,
            forecastList = avgForecastList
        )
    }

}

fun getDayFromDate(dateString: String): String {
    val simpleDateFormat = SimpleDateFormat("YYYY-MM-DD", Locale.getDefault())
    val date = simpleDateFormat.parse(dateString)

    val calendar = Calendar.getInstance()
    calendar.time = date

    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        1 -> "Sunday"
        2 -> "Monday"
        3 -> "Tuesday"
        4 -> "Wednesday"
        5 -> "Thursday"
        6 -> "Friday"
        7 -> "Saturday"
        else -> throw IllegalArgumentException("Invalid Date")
    }
}