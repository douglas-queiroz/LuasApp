package com.douglas.luasapp.di.module

import android.content.Context
import com.douglas.luasapp.LuasApp
import com.douglas.luasapp.helper.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HelperModule(private val app: LuasApp) {

    @Singleton
    @Provides
    fun providesContext() : Context = app

    @Singleton
    @Provides
    fun providesInternetConnectionHelper(context: Context)
            : InternetConnectionHelper = InternetConnectionHelperImpl(context)

    @Singleton
    @Provides
    fun providesLogHelper(): LogHelper = LogHelperImpl()

    @Singleton
    @Provides
    fun providesTimeHelper(): TimeHelper = TimeHelperImpl()
}