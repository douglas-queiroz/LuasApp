package com.douglas.luasapp.service.helper

import com.douglas.luasapp.BuildConfig
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class RetrofitCreator {

    companion object {

        private fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

        fun createRetrofit(): Retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API)
                .addConverterFactory(TikXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(provideOkHttpClient())
                .build()
    }
}