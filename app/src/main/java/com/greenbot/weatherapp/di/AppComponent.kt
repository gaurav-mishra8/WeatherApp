package com.greenbot.weatherapp.di

import android.app.Application
import com.greenbot.weatherapp.WeatherApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AppModule::class, AndroidInjectionModule::class, DataModule::class,
        ActivityModule::class, ViewModelModule::class
    )
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: WeatherApplication)
}