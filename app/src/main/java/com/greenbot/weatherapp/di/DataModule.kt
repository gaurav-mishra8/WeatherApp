package com.greenbot.weatherapp.di

import com.greenbot.data.WeatherDataSource
import com.greenbot.data.remote.WeatherRemoteDataSource
import com.greenbot.data.remote.WeatherRemoteDataSourceImpl
import com.greenbot.data.remote.service.WeatherForecastService
import com.greenbot.data.remote.service.WeatherForecastServiceFactory
import com.greenbot.domain.repository.WeatherRepository
import com.greenbot.weatherapp.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class DataModule {

    @Binds
    abstract fun bindWeatherForecastRepository(weatherDataSource: WeatherDataSource): WeatherRepository

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: WeatherRemoteDataSourceImpl): WeatherRemoteDataSource

    @Module
    companion object {
        @Provides
        fun provideWeatherForecastService(): WeatherForecastService {
            return WeatherForecastServiceFactory.buildWeatherForecastService(BuildConfig.DEBUG)
        }
    }

}