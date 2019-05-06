package com.greenbot.weatherapp

data class Resource<T>(val status: ResourceStatus, val data: T? = null, val error: String? = null)

enum class ResourceStatus {
    LOADING, SUCCESS, ERROR
}