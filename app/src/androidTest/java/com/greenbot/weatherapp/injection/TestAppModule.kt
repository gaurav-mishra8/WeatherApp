package com.greenbot.weatherapp.injection

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class TestAppModule {

    @Binds
    abstract fun provideAppContext(app: Application): Context
}