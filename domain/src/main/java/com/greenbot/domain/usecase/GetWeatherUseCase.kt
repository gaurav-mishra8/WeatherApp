package com.greenbot.domain.usecase

import com.greenbot.domain.ExecutionThread
import com.greenbot.domain.model.WeatherForecast
import com.greenbot.domain.repository.WeatherRepository
import io.reactivex.Single

/**
 * Usecase class to fetch weather forecast for provided number of days
 */
class GetWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    executionThread: ExecutionThread
) : SingleUseCase<WeatherForecast, GetWeatherUseCase.Params>(executionThread) {

    override fun buildUseCaseSingle(params: Params?): Single<WeatherForecast> {
        if (params == null) throw IllegalStateException("No location info provided")
        return weatherRepository.getWeatherForecast(params.lat, params.lon, params.days)
    }

    data class Params(val lat: Double, val lon: Double, val days: Int)

}