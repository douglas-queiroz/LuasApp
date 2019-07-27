package com.douglas.luasapp.domain

import com.douglas.luasapp.domain.exception.InvalidParamException
import com.douglas.luasapp.domain.exception.NoInternetConnectionException
import com.douglas.luasapp.helper.InternetConnectionHelper
import com.douglas.luasapp.helper.InternetConnectionHelper.Companion.INTERNET_STATUS_NO_CONNECTION
import com.douglas.luasapp.helper.InternetConnectionHelper.Companion.INTERNET_STATUS_OK
import com.douglas.luasapp.service.StopInfoService
import com.douglas.luasapp.service.model.DirectionResponse
import com.douglas.luasapp.service.model.StopInfoResponse
import com.douglas.luasapp.service.model.TramResponse
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*

class GetStopForecastUseCaseTest {

    @Mock
    lateinit var internetConnectionHelper: InternetConnectionHelper

    @Mock
    lateinit var stopInfoService: StopInfoService

    private lateinit var target: GetStopForecastUseCase

    private val tramResponse = TramResponse (
        "deuMins",
        "destination"
    )

    val stop = "stop"

    private val directionResponse = DirectionResponse (
        "name",
        arrayListOf(tramResponse)
    )

    private val stopInfoResponse = StopInfoResponse(
        Date(),
        "stop",
        "stopAbv",
        "message",
        arrayListOf(directionResponse)
    )

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        target = GetStopForecastUseCaseImpl(internetConnectionHelper, stopInfoService)
    }

    @Test
    fun `When has no internet connection`() {

        `when`(internetConnectionHelper.checkInternetConnection()).thenReturn(INTERNET_STATUS_NO_CONNECTION)
        `when`(stopInfoService.fetchStopInfo(stop)).thenReturn(Observable.just(stopInfoResponse))

        val testObserver = target.getStopForecast(stop).test()

        testObserver.assertNotComplete()
        testObserver.assertError(NoInternetConnectionException::class.java)
    }

    @Test
    fun `When stop param is empty`() {

        val emptyStop = ""

        `when`(internetConnectionHelper.checkInternetConnection()).thenReturn(INTERNET_STATUS_OK)
        `when`(stopInfoService.fetchStopInfo(emptyStop)).thenReturn(Observable.just(stopInfoResponse))

        val testObserver = target.getStopForecast(emptyStop).test()

        testObserver.assertNotComplete()
        testObserver.assertError(InvalidParamException::class.java)
    }

    @Test
    fun `When it works fine`() {

        `when`(internetConnectionHelper.checkInternetConnection()).thenReturn(INTERNET_STATUS_OK)
        `when`(stopInfoService.fetchStopInfo(stop)).thenReturn(Observable.just(stopInfoResponse))

        val testObserver = target.getStopForecast(stop).test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
    }
}