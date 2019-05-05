package com.greenbot.weatherapp

import com.greenbot.domain.ExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MainThread @Inject constructor() : ExecutionThread {
    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}