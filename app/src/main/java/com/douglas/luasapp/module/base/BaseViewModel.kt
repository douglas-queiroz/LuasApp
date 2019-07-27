package com.douglas.luasapp.module.base

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.douglas.luasapp.R
import com.douglas.luasapp.helper.LogHelper
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(private val logHelper: LogHelper): ViewModel(), LifecycleObserver {

    protected val subscriptions = CompositeDisposable()
    val loadingStatus = MutableLiveData<Boolean>()
    val showErrorMessage = MutableLiveData<Int>()

    override fun onCleared() {
        super.onCleared()

        subscriptions.clear()
    }

    protected fun onError(error: Throwable) {

        loadingStatus.value = false

        showErrorMessage.value = R.string.error_default

        logHelper.logError(error)

    }
}