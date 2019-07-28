package com.douglas.luasapp.di.module

import com.douglas.luasapp.service.StopInfoService
import com.douglas.luasapp.service.helper.RetrofitCreator
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class ServiceModule {

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit = RetrofitCreator.createRetrofit()

    @Singleton
    @Provides
    fun providesStopInfoService(retrofit: Retrofit) : StopInfoService = retrofit.create(StopInfoService::class.java)
}