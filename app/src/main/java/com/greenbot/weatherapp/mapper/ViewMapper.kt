package com.greenbot.weatherapp.mapper

interface ViewMapper<in D, out V> {
    fun mapToView(data: D): V
}