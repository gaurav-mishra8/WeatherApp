package com.greenbot.weatherapp.injection

import com.greenbot.data.remote.WeatherRemoteDataSource
import com.greenbot.data.remote.service.WeatherForecastService
import com.greenbot.domain.repository.WeatherRepository
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestDataModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideWeatherForecastRepository(): WeatherRepository {
        return mock()
    }

    @Provides
    @JvmStatic
    fun provideWeatherForecastService(): WeatherForecastService {
        return mock()
    }

    @Provides
    @JvmStatic
    fun provideRemoteDataSource(): WeatherRemoteDataSource {
        return mock()
    }

}