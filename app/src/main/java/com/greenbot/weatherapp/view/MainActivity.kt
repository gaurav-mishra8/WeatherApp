package com.greenbot.weatherapp.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.greenbot.weatherapp.R
import com.greenbot.weatherapp.ResourceStatus
import com.greenbot.weatherapp.ViewModelFactory
import com.greenbot.weatherapp.model.WeatherForecastViewData
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.home_content_layout.*
import kotlinx.android.synthetic.main.home_error_layout.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        home_rv_forecast.apply {
            hasFixedSize()
            adapter = ForecastListAdapter()
        }

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
                showErrorState("")
            }
        })
    }


    private fun showLoadingState() {
        home_error_view.visibility = View.GONE
        home_content_view.visibility = View.GONE
        home_progressbar.visibility = View.VISIBLE
    }

    private fun showSuccessState(weatherForecast: WeatherForecastViewData) {
        home_error_view.visibility = View.GONE
        home_content_view.visibility = View.VISIBLE
        home_progressbar.visibility = View.GONE

        home_tv_temperature.text = weatherForecast.currentTemp.toString()
        home_tv_location.text = weatherForecast.locationName
        val forecastListAdapter = home_rv_forecast.adapter as ForecastListAdapter
        forecastListAdapter.setForecastList(weatherForecast.forecastList)

    }

    private fun showErrorState(errorMsg: String) {
        home_error_view.visibility = View.VISIBLE
        home_content_view.visibility = View.GONE
        home_progressbar.visibility = View.GONE

        home_tv_error.text = errorMsg

    }
}
