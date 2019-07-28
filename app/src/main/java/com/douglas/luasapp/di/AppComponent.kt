package com.douglas.luasapp.di

import com.douglas.luasapp.di.module.UseCaseModule
import com.douglas.luasapp.di.module.HelperModule
import com.douglas.luasapp.di.module.ServiceModule
import com.douglas.luasapp.di.module.ViewModelModule
import com.douglas.luasapp.module.stopforecast.StopForecastActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (
    modules = [
        HelperModule::class,
        ServiceModule::class,
        UseCaseModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(stopForecastActivity: StopForecastActivity)
}