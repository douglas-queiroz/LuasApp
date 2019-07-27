package com.douglas.luasapp.domain

import com.douglas.luasapp.domain.model.StopForecast
import io.reactivex.Observable

interface GetStopForecastUseCase {

    fun getStopForecast(stop: String): Observable<List<StopForecast>>
}