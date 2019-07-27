package com.douglas.luasapp.module.stopforecast

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.douglas.luasapp.R
import com.douglas.luasapp.RxImmediateSchedulerRule
import com.douglas.luasapp.TestObserver
import com.douglas.luasapp.domain.GetStopForecastUseCase
import com.douglas.luasapp.domain.exception.InvalidParamException
import com.douglas.luasapp.domain.exception.NoInternetConnectionException
import com.douglas.luasapp.domain.model.Stop
import com.douglas.luasapp.domain.model.StopForecast
import com.douglas.luasapp.helper.LogHelper
import com.douglas.luasapp.helper.TimeHelper
import com.douglas.luasapp.module.stopforecast.StopForecastViewModel.Companion.STOP_MARLBOROUGH
import com.douglas.luasapp.module.stopforecast.StopForecastViewModel.Companion.STOP_STILLORGAN
import com.douglas.luasapp.testObserver
import io.reactivex.Observable
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class StopForecastViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var logHelper: LogHelper

    @Mock
    lateinit var timeHelper: TimeHelper

    @Mock
    lateinit var getStopForecastUseCase: GetStopForecastUseCase

    private val stop = Stop(
        "name",
        "message"
    )

    private val forecast = StopForecast (
        stop,
        "direction",
        "due",
        "destination"
    )

    private val listForecast = listOf(forecast)

    private lateinit var target: StopForecastViewModel

    private lateinit var loadingStatusStore: TestObserver<Boolean>
    private lateinit var showMessageStatusStore: TestObserver<Int>
    private lateinit var stopForecastsStore: TestObserver<List<StopForecast>>

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        target = StopForecastViewModel(logHelper, timeHelper, getStopForecastUseCase)

        loadingStatusStore = target.loadingStatus.testObserver()
        showMessageStatusStore = target.showErrorMessage.testObserver()
        stopForecastsStore = target.stopForecastsLiveData.testObserver()
    }

    @Test
    fun `When it is 10h30m`() {

        `when`(timeHelper.getCurrentHour()).thenReturn(10)
        `when`(timeHelper.getCurrentMin()).thenReturn(30)
        `when`(getStopForecastUseCase.getStopForecast(STOP_STILLORGAN))
            .thenReturn(Observable.just(listForecast))

        target.loadStopForecasts()

        assertEquals(true, loadingStatusStore.observedValues[0])
        assertEquals(false, loadingStatusStore.observedValues[1])
        assertEquals(0, showMessageStatusStore.observedValues.count())
        assertEquals(listForecast, stopForecastsStore.observedValues[0])
    }

    @Test
    fun `When it is 12h00m`() {

        `when`(timeHelper.getCurrentHour()).thenReturn(12)
        `when`(timeHelper.getCurrentMin()).thenReturn(0)
        `when`(getStopForecastUseCase.getStopForecast(STOP_STILLORGAN))
            .thenReturn(Observable.just(listForecast))

        target.loadStopForecasts()

        assertEquals(true, loadingStatusStore.observedValues[0])
        assertEquals(false, loadingStatusStore.observedValues[1])
        assertEquals(0, showMessageStatusStore.observedValues.count())
        assertEquals(listForecast, stopForecastsStore.observedValues[0])
    }

    @Test
    fun `When it is 12h01m`() {

        `when`(timeHelper.getCurrentHour()).thenReturn(12)
        `when`(timeHelper.getCurrentMin()).thenReturn(1)
        `when`(getStopForecastUseCase.getStopForecast(STOP_MARLBOROUGH))
            .thenReturn(Observable.just(listForecast))

        target.loadStopForecasts()

        assertEquals(true, loadingStatusStore.observedValues[0])
        assertEquals(false, loadingStatusStore.observedValues[1])
        assertEquals(0, showMessageStatusStore.observedValues.count())
        assertEquals(listForecast, stopForecastsStore.observedValues[0])
    }

    @Test
    fun `When getStopForecastUseCase throws NoInternetConnectionException`() {

        `when`(timeHelper.getCurrentHour()).thenReturn(10)
        `when`(timeHelper.getCurrentMin()).thenReturn(30)
        `when`(getStopForecastUseCase.getStopForecast(STOP_STILLORGAN))
            .thenReturn(Observable.error(NoInternetConnectionException()))

        target.loadStopForecasts()

        assertEquals(true, loadingStatusStore.observedValues[0])
        assertEquals(false, loadingStatusStore.observedValues[1])
        assertEquals(R.string.error_no_internet_connection, showMessageStatusStore.observedValues[0])
        assertEquals(0, stopForecastsStore.observedValues.count())
    }

    @Test
    fun `When getStopForecastUseCase throws InvalidParamException`() {

        `when`(timeHelper.getCurrentHour()).thenReturn(10)
        `when`(timeHelper.getCurrentMin()).thenReturn(30)
        `when`(getStopForecastUseCase.getStopForecast(STOP_STILLORGAN))
            .thenReturn(Observable.error(InvalidParamException()))

        target.loadStopForecasts()

        assertEquals(true, loadingStatusStore.observedValues[0])
        assertEquals(false, loadingStatusStore.observedValues[1])
        assertEquals(R.string.error_default, showMessageStatusStore.observedValues[0])
        assertEquals(0, stopForecastsStore.observedValues.count())
    }
}