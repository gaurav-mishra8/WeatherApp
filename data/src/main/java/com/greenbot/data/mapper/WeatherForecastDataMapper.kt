package com.greenbot.data.mapper

import com.greenbot.data.model.WeatherForecastResponse
import com.greenbot.domain.model.Forecast
import com.greenbot.domain.model.WeatherForecast
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WeatherForecastDataMapper @Inject constructor() :
    DataModelMapper<WeatherForecastResponse, WeatherForecast> {

    override fun mapFromDataModel(dataModel: WeatherForecastResponse): WeatherForecast {
        val avgForecastList = mutableListOf<Forecast>()

        dataModel.forecast!!.forecastDayList.takeLast(4).forEach {
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

private fun getDayFromDate(dateString: String): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
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