package com.douglas.luasapp.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.douglas.luasapp.di.ViewModelFactory
import com.douglas.luasapp.di.ViewModelKey
import com.douglas.luasapp.module.stopforecast.StopForecastViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StopForecastViewModel::class)
    internal abstract fun bindsStopForecastViewModel(stopForecastViewModel: StopForecastViewModel) : ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}