package com.douglas.luasapp.service

import com.douglas.luasapp.service.model.StopInfoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface StopInfoService {

    @GET("/xml/get.ashx")
    fun fetchStopInfo(@Query("stop") stop: String,
                      @Query("action") action: String? = "forecast",
                      @Query("encrypt") encrypt: String? = "false"): Observable<StopInfoResponse>
}