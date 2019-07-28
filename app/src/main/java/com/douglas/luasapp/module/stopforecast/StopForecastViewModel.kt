package com.douglas.luasapp.module.stopforecast

import android.arch.lifecycle.MutableLiveData
import com.douglas.luasapp.R
import com.douglas.luasapp.domain.GetStopForecastUseCase
import com.douglas.luasapp.domain.exception.NoInternetConnectionException
import com.douglas.luasapp.domain.model.StopInfo
import com.douglas.luasapp.domain.model.StopForecast
import com.douglas.luasapp.helper.LogHelper
import com.douglas.luasapp.helper.TimeHelper
import com.douglas.luasapp.module.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class StopForecastViewModel @Inject constructor(logHelper: LogHelper,
                                                private val timeHelper: TimeHelper,
                                                private val getStopForecastUseCase:
                                                GetStopForecastUseCase): BaseViewModel(logHelper) {

    companion object {
        const val STOP_MARLBOROUGH = "mar"
        const val STOP_STILLORGAN = "sti"
    }

    val stopForecastsLiveData = MutableLiveData<List<StopForecast>>()
    val stopLiveData = MutableLiveData<StopInfo>()

    fun loadStopForecasts() {

        loadingStatus.value = true

        val stop = getStopAccordingTime()

        val observable = getStopForecastUseCase.getStopForecast(stop)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::onLoadSuccess, this::onLoadError)

        subscriptions.add(observable)

    }

    private fun onLoadSuccess(stop: StopInfo) {

        loadingStatus.value = false

        stopForecastsLiveData.value = stop.stopForecasts

        stopLiveData.value = stop
    }

    private fun onLoadError(error: Throwable) {

        when (error) {
            is NoInternetConnectionException -> showNoInternetConnectionMessage()
            else -> super.onError(error)
        }

    }

    private fun showNoInternetConnectionMessage() {

        loadingStatus.value = false

        showErrorMessage.value = R.string.error_no_internet_connection
    }

    private fun getStopAccordingTime(): String {

        val hour = timeHelper.getCurrentHour()
        val min = timeHelper.getCurrentMin()

        return if (hour < 12 || (hour == 12 && min == 0)) {

            STOP_STILLORGAN

        } else {

            STOP_MARLBOROUGH
        }
    }
}