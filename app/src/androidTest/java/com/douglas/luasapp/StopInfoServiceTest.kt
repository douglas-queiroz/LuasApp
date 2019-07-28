package com.douglas.luasapp

import android.support.test.runner.AndroidJUnit4
import com.douglas.luasapp.service.StopInfoService
import com.douglas.luasapp.service.helper.RetrofitCreator
import io.reactivex.disposables.CompositeDisposable
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.fail
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class StopInfoServiceTest {

    private val subscription = CompositeDisposable()

    @After
    fun tearDown() {
        subscription.clear()
    }

    @Test
    fun useAppContext() {

        val signal = CountDownLatch(1)

        val stopInfoService = RetrofitCreator.createRetrofit().create(StopInfoService::class.java)

        subscription.add(stopInfoService.fetchStopInfo("mar")
            .subscribe({

                assertNotNull(it.message)
                assertNotNull(it.created)
                assertNotNull(it.stop)
                assertNotNull(it.stopAbv)

                signal.countDown()
            },{
                fail()
                signal.countDown()
            }))

        signal.await()
    }
}