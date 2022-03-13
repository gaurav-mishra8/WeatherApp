package com.greenbot.weatherapp.di

import com.greenbot.domain.ExecutionThread
import com.greenbot.weatherapp.MainThread
import com.greenbot.weatherapp.view.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun provideMainActivity(): MainActivity

    @Binds
    abstract fun bindExecutionThread(mainThread: MainThread): ExecutionThread
}