package com.greenbot.weatherapp

import android.content.Context
import android.util.TypedValue


fun convertDpToPixel(context: Context, dip: Float): Int {
    val r = context.getResources()
    val px = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dip,
        r.displayMetrics
    )
    return px.toInt()
}