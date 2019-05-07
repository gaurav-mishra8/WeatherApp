package com.greenbot.weatherapp.injection

import android.app.Application
import com.greenbot.domain.repository.WeatherRepository
import com.greenbot.weatherapp.TestApplication
import com.greenbot.weatherapp.di.ActivityModule
import com.greenbot.weatherapp.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidSupportInjectionModule::class,
        TestAppModule::class,
        TestDataModule::class,
        ViewModelModule::class,
        ActivityModule::class
    )
)
interface TestApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): TestApplicationComponent.Builder

        fun build(): TestApplicationComponent
    }

    fun weatherRepository(): WeatherRepository

    fun inject(application: TestApplication)

}