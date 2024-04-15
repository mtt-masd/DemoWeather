package com.android.clix.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseViewModel : ViewModel() {
    protected val compositeDisposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposables.dispose()
    }
}

fun Disposable.addTo(compositeDisposables: CompositeDisposable) {
    compositeDisposables.add(this)
}