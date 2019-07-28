package com.douglas.luasapp.domain

import com.douglas.luasapp.domain.model.StopInfo
import io.reactivex.Observable

interface GetStopForecastUseCase {

    fun getStopForecast(stop: String): Observable<StopInfo>
}