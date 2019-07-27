package com.douglas.luasapp.domain

import com.douglas.luasapp.domain.exception.InvalidParamException
import com.douglas.luasapp.domain.exception.NoInternetConnectionException
import com.douglas.luasapp.helper.InternetConnectionHelper
import com.douglas.luasapp.helper.InternetConnectionHelper.Companion.INTERNET_STATUS_NO_CONNECTION
import com.douglas.luasapp.domain.model.Stop
import com.douglas.luasapp.domain.model.StopForecast
import com.douglas.luasapp.service.StopInfoService
import com.douglas.luasapp.service.model.StopInfoResponse
import io.reactivex.Completable
import io.reactivex.Observable

class GetStopForecastUseCaseImpl(private val internetConnectionHelper: InternetConnectionHelper,
                                 private val stopInfoService: StopInfoService): GetStopForecastUseCase {

    override fun getStopForecast(stop: String): Observable<List<StopForecast>> {

        return validateParams(stop)
            .andThen(checkPreRequirements())
            .andThen(requestService(stop))
            .flatMap { return@flatMap generateStopForecast(it) }

    }

    private fun validateParams(stop: String): Completable {

        return if (stop.isEmpty()) {

            Completable.error(InvalidParamException())

        } else {

            Completable.complete()

        }
    }

    private fun checkPreRequirements(): Completable {

        return if (internetConnectionHelper.checkInternetConnection() == INTERNET_STATUS_NO_CONNECTION) {

            Completable.error(NoInternetConnectionException())

        } else {

            Completable.complete()

        }
    }

    private fun requestService(stop: String): Observable<StopInfoResponse> {

        return stopInfoService.fetchStopInfo(stop)

    }

    private fun generateStopForecast(stopInfoResponse: StopInfoResponse): Observable<List<StopForecast>> {

        return Observable.create { emitter ->

            val result = arrayListOf<StopForecast>()

            val stop = Stop(
                stopInfoResponse.stop,
                stopInfoResponse.message
            )

            stopInfoResponse.direction?.forEach { direction ->

                val directionName = direction.name

                direction.tram?.forEach { tram ->

                    val forecast = StopForecast(
                        stop,
                        directionName,
                        tram.dueMins,
                        tram.destination
                    )

                    result.add(forecast)
                }
            }

            emitter.onNext(result)
            emitter.onComplete()
        }
    }
}