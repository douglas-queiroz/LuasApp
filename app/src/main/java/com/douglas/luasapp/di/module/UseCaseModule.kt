package com.douglas.luasapp.di.module

import com.douglas.luasapp.domain.GetStopForecastUseCase
import com.douglas.luasapp.domain.GetStopForecastUseCaseImpl
import com.douglas.luasapp.helper.InternetConnectionHelper
import com.douglas.luasapp.service.StopInfoService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun providesCheckLoginUseCase(internetConnectionHelper: InternetConnectionHelper,
                                  stopInfoService: StopInfoService):
            GetStopForecastUseCase = GetStopForecastUseCaseImpl(internetConnectionHelper, stopInfoService)

}