package com.greenbot.weatherapp.location

import android.Manifest
import android.annotation.SuppressLint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*


class LocationProvider(
    private val context: Context,
    private val lifecycle: Lifecycle,
    private var locationListener: LocationListener
) : LifecycleObserver {

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    init {
        lifecycle.addObserver(this)
    }

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val lastLocation = locationResult.lastLocation
            val longitude = lastLocation.longitude
            val latitude = lastLocation.latitude
            val location = LocationModel(longitude, latitude)
            locationListener.updateLocation(location)
        }
    }

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val locationProviderClient = getFusedLocationProviderClient()
        val locationRequest = LocationRequest.create()

        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun getFusedLocationProviderClient(): FusedLocationProviderClient {
        if (fusedLocationProviderClient == null) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        }
        return fusedLocationProviderClient!!
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun unregisterForLocationUpdates() {
        fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unregisterObserver() {
        fusedLocationProviderClient = null
        lifecycle.removeObserver(this)
    }
}