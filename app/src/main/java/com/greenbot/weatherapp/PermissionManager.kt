package com.greenbot.weatherapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat

object PermissionManager {

    const val PERMISSIONS_REQUEST_GET_LOCATION = 101

    fun checkSelfPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            return true
        }
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun verifyPermissions(grantResults: IntArray): Boolean {
        if (grantResults.size < 1) {
            return false
        }
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
}