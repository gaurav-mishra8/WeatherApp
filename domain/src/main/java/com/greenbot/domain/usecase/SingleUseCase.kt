package com.greenbot.domain.usecase

import com.greenbot.domain.ExecutionThread
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Base class for usecases that return a Single Observable
 */
abstract class SingleUseCase<T, in Params>(private val executionThread: ExecutionThread) {

    private val disposables = CompositeDisposable()

    private fun addDisposables(disposable: Disposable) {
        disposables.add(disposable)
    }

    protected abstract fun buildUseCaseSingle(params: Params? = null): Single<T>

    fun execute(singleObserver: DisposableSingleObserver<T>, params: Params? = null) {

        val disposable = buildUseCaseSingle(params)
            .subscribeOn(Schedulers.io())
            .observeOn(executionThread.scheduler)
            .subscribeWith(singleObserver)

        addDisposables(disposable)

    }

    fun clearDisposables() {
        if (!disposables.isDisposed)
            disposables.dispose()
    }


}