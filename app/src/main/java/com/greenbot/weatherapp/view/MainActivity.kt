package com.greenbot.weatherapp.view

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.greenbot.weatherapp.PermissionManager
import com.greenbot.weatherapp.PermissionManager.PERMISSIONS_REQUEST_GET_LOCATION
import com.greenbot.weatherapp.R
import com.greenbot.weatherapp.ResourceStatus
import com.greenbot.weatherapp.ViewModelFactory
import com.greenbot.weatherapp.location.LocationListener
import com.greenbot.weatherapp.location.LocationModel
import com.greenbot.weatherapp.location.LocationProvider
import com.greenbot.weatherapp.model.WeatherForecastViewData
import com.greenbot.weatherapp.view.MainViewModel.Companion.ACTION_LOCATION_PERMISSION_DECLINED
import com.greenbot.weatherapp.view.MainViewModel.Companion.ACTION_RETRY
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.home_content_layout.*
import kotlinx.android.synthetic.main.home_error_layout.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), LocationListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var mainViewModel: MainViewModel
    private lateinit var locationProvider: LocationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        home_rv_forecast.apply {
            hasFixedSize()
            adapter = ForecastListAdapter()
            addItemDecoration(VerticalDividerItemDecoration(this@MainActivity))
        }

        home_btn_retry.setOnClickListener {
            mainViewModel.onUserEvent(ACTION_RETRY)
        }

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        observeViewModel()

        locationProvider = LocationProvider(this, this.lifecycle, this)
        requestLocationPermission()
    }

    private fun requestLocationPermission(isForced: Boolean = false) {
        if (!PermissionManager.checkSelfPermission(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), PERMISSIONS_REQUEST_GET_LOCATION
                )
            }
        } else {
            fetchLocation(isForced)
        }
    }

    private fun fetchLocation(isForced: Boolean) {
        locationProvider.requestLocationUpdates(isForced)
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

        mainViewModel.getCommand().observe(this, Observer {
            when (it) {
                Command.FetchLocation -> {
                    requestLocationPermission(true)
                }
            }
        })
    }


    private fun showLoadingState() {
        home_error_view.visibility = View.GONE
        home_content_view.visibility = View.GONE
        home_progress_container.visibility = View.VISIBLE

        val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation)
        home_progress_view.animation = rotateAnimation
    }

    private fun showSuccessState(weatherForecast: WeatherForecastViewData) {
        home_error_view.visibility = View.GONE
        home_content_view.visibility = View.VISIBLE
        home_progress_container.visibility = View.GONE

        val slideInBottom = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom)
        home_rv_container.animation = slideInBottom

        home_tv_temperature.text = weatherForecast.currentTemp.toString()
        home_tv_location.text = weatherForecast.locationName
        val forecastListAdapter = home_rv_forecast.adapter as ForecastListAdapter
        forecastListAdapter.setForecastList(weatherForecast.forecastList)
    }

    private fun showErrorState(errorMsg: String) {
        home_error_view.visibility = View.VISIBLE
        home_content_view.visibility = View.GONE
        home_progress_container.visibility = View.GONE
        home_tv_error.text = errorMsg
    }

    override fun updateLocation(locationModel: LocationModel) {
        mainViewModel.fetchWeatherForecastDetails(locationModel.latitude, locationModel.longitude)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_GET_LOCATION) {
            if (PermissionManager.verifyPermissions(grantResults)) {
                fetchLocation(false)
            } else {
                mainViewModel.onUserEvent(ACTION_LOCATION_PERMISSION_DECLINED)
            }
        }
    }
}
