package com.greenbot.weatherapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.greenbot.domain.model.WeatherForecast
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        observeViewModel()

        mainViewModel.fetchWeatherForecastDetails(28.7, 78.5)

    }

    private fun observeViewModel() {
        mainViewModel.getWeatherForecast().observe(this, Observer {
            it?.let {
                when (it.status) {
                    ResourceStatus.LOADING -> {
                        showLoadingState()
                    }
                    ResourceStatus.SUCCESS -> {
                        showSuccessState(it.data!!)
                    }
                    ResourceStatus.ERROR -> {
                        showErrorState(it.error!!)
                    }
                }
            } ?: run {

            }
        })
    }


    private fun showLoadingState() {

    }

    private fun showSuccessState(weatherForecast: WeatherForecast) {

    }

    private fun showErrorState(errorMsg: String) {

    }
}
