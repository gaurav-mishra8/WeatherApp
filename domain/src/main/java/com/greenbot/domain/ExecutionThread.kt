package com.greenbot.domain

import io.reactivex.Scheduler

/**
 * Interface to provide the thread on which domain result is to be observed by the scubscriber.
 * This info is passed on by the presentaion layer while interacting with domain layer
 *
 * Helpful for mocking while testing
 */
interface ExecutionThread {
    val scheduler: Scheduler
}