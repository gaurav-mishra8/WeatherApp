package com.greenbot.domain.usecase

import MockWeatherData
import com.greenbot.domain.ExecutionThread
import com.greenbot.domain.model.WeatherForecast
import com.greenbot.domain.repository.WeatherRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetWeatherUseCaseTest {

    @Mock
    private lateinit var weatherRepository: WeatherRepository
    @Mock
    private lateinit var executionThread: ExecutionThread

    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getWeatherUseCase = GetWeatherUseCase(weatherRepository, executionThread)
    }

    @Test
    fun getWeatherCompletesSuccessfully() {
        mockWeatherRepositoryResponse(Single.just(MockWeatherData.getWeatherForecast()))
        val testObserver = getWeatherUseCase.buildUseCaseSingle(getMockParams()).test()

        testObserver.assertComplete()
    }

    @Test
    fun getWeatherCallsRepositoryOnce() {
        mockWeatherRepositoryResponse(Single.just(MockWeatherData.getWeatherForecast()))
        val testObserver = getWeatherUseCase.buildUseCaseSingle(getMockParams()).test()

        verify(weatherRepository, times(1)).getWeatherForecast(any(), any(), any())
    }

    @Test
    fun getWeatherReturnsCorrectData() {
        val weatherForecast = MockWeatherData.getWeatherForecast()
        mockWeatherRepositoryResponse(Single.just(weatherForecast))

        val testObserver = getWeatherUseCase.buildUseCaseSingle(getMockParams()).test()

        testObserver.assertValues(weatherForecast)
    }

    @Test
    fun getWeatherReturnsError() {
        val throwable = Throwable("something went wrong")
        mockWeatherRepositoryResponse(Single.error(throwable))
        val testObserver = getWeatherUseCase.buildUseCaseSingle(getMockParams()).test()

        testObserver.assertError(throwable)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getWeatherThrowsExceptionForNoParams() {
        mockWeatherRepositoryResponse(Single.just(MockWeatherData.getWeatherForecast()))
        val testObserver = getWeatherUseCase.buildUseCaseSingle(null).test()
    }


    private fun getMockParams() = GetWeatherUseCase.Params(any(), any(), any())

    private fun mockWeatherRepositoryResponse(observable: Single<WeatherForecast>) {
        whenever(weatherRepository.getWeatherForecast(any(), any(), any())).thenReturn(observable)
    }


}