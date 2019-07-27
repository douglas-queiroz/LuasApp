package com.douglas.luasapp.domain

import com.douglas.luasapp.domain.exception.NoInternetConnectionException
import org.junit.Before

class GetStopForecastUseCaseTest {

    companion object {
        const val STOP = "stop"
    }

    private lateinit var target: GetStopForecastUseCase

    @Before
    fun setUp() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun `When has no internet connection`() {

        val testObserver = target.getStopForecast(STOP).test()

        testObserver.assertNotComplete()
        testObserver.assertError(NoInternetConnectionException::class.java)
    }

    fun `When stop param is empty`() {

        val testObserver = target.getStopForecast("").test()

        testObserver.assertNotComplete()
        testObserver.assertError(NoInternetConnectionException::class.java)
    }

    fun `When it works fine`() {

        val testObserver = target.getStopForecast(STOP).test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
    }
}