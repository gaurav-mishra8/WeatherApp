package com.greenbot.weatherapp.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.greenbot.domain.model.WeatherForecast
import com.greenbot.domain.usecase.GetWeatherUseCase
import com.greenbot.weatherapp.ResourceStatus
import com.greenbot.weatherapp.mapper.WeatherForecastViewMapper
import com.greenbot.weatherapp.model.WeatherForecastViewData
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val getWeatherUseCase = mock<GetWeatherUseCase>()
    val mapper = mock<WeatherForecastViewMapper>()

    @Captor
    val captor = argumentCaptor<DisposableSingleObserver<WeatherForecast>>()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(getWeatherUseCase, mapper)
    }

    @Test
    fun fetchWeatherReturnsSuccess() {
        val weatherForecast = WeatherDataFactory.buildMockWeatherData()
        val weatherForecastViewData = WeatherDataFactory.buildMockWeatherViewData()
        stubWeatherViewMapper(weatherForecast, weatherForecastViewData)

        val params = WeatherDataFactory.getWeatherParams()

        mainViewModel.fetchWeatherForecastDetails(params.lat, params.lon)

        verify(getWeatherUseCase).execute(captor.capture(), eq(params))
        captor.firstValue.onSuccess(weatherForecast)

        assertEquals(
            ResourceStatus.SUCCESS,
            mainViewModel.getWeatherForecast().value?.status
        )

    }

    @Test
    fun fetchWeatherReturnsData() {
        val weatherForecast = WeatherDataFactory.buildMockWeatherData()
        val weatherForecastViewData = WeatherDataFactory.buildMockWeatherViewData()
        stubWeatherViewMapper(weatherForecast, weatherForecastViewData)

        val params = WeatherDataFactory.getWeatherParams()

        mainViewModel.fetchWeatherForecastDetails(params.lat, params.lon)

        verify(getWeatherUseCase).execute(captor.capture(), eq(params))
        captor.firstValue.onSuccess(weatherForecast)

        assertEquals(
            weatherForecastViewData,
            mainViewModel.getWeatherForecast().value?.data
        )

    }

    @Test
    fun fetchWeatherReturnsError() {
        val error = Throwable(WeatherDataFactory.getErrorString())

        val params = WeatherDataFactory.getWeatherParams()

        mainViewModel.fetchWeatherForecastDetails(params.lat, params.lon)

        verify(getWeatherUseCase).execute(captor.capture(), eq(params))
        captor.firstValue.onError(error)

        assertEquals(
            ResourceStatus.ERROR,
            mainViewModel.getWeatherForecast().value?.status
        )

    }

    @Test
    fun fetchWeatherErrorCheckMessage() {
        val errorMsg = WeatherDataFactory.getErrorString()
        val error = Throwable(errorMsg)

        val params = WeatherDataFactory.getWeatherParams()

        mainViewModel.fetchWeatherForecastDetails(params.lat, params.lon)

        verify(getWeatherUseCase).execute(captor.capture(), eq(params))
        captor.firstValue.onError(error)

        assertEquals(
            errorMsg,
            mainViewModel.getWeatherForecast().value?.error
        )

    }

    private fun stubWeatherViewMapper(
        weatherForecast: WeatherForecast,
        weatherViewData: WeatherForecastViewData
    ) {
        whenever(mapper.mapToView(weatherForecast)).thenReturn(weatherViewData)
    }


}