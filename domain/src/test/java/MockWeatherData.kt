import com.greenbot.domain.model.Forecast
import com.greenbot.domain.model.WeatherForecast
import java.util.*

object MockWeatherData {

    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    private fun randomString(): String = java.util.UUID.randomUUID().toString()

    private fun getRandomDay(day: Int): String = days[day]

    private fun getRandomTemp(): Double = Random().nextDouble()

    private fun buildForecastItem(day: Int): Forecast = Forecast(getRandomDay(day), getRandomTemp())

    private fun buildForecastList(day: Int = 1, days: Int): List<Forecast> {
        val forecastList = mutableListOf<Forecast>()
        repeat(days) {
            forecastList.add(buildForecastItem(day))
        }
        return forecastList
    }

    fun getWeatherForecast(day: Int = 1, days: Int = 4): WeatherForecast {
        val weatherForecast = WeatherForecast(
            locationName = randomString(),
            currentTemp = getRandomTemp(),
            forecastList = buildForecastList(day, days)
        )

        return weatherForecast
    }
}