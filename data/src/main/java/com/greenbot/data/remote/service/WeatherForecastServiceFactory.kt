package com.greenbot.data.remote.service

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object WeatherForecastServiceFactory {

    fun buildWeatherForecastService(isDebug: Boolean): WeatherForecastService {
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor((isDebug))
        )
        return makeWeatherForecastService(okHttpClient, Gson())
    }

    private fun makeWeatherForecastService(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): WeatherForecastService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(WeatherForecastService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url()

                val newUrl = originalUrl.newBuilder()
                    .addQueryParameter("key", "99b9bc65d95e4580ab062554221303")
                    .build()

                val requestBuilder = originalRequest.newBuilder().url(newUrl)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

}