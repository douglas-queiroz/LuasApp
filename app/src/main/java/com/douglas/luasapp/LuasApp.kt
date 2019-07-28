package com.douglas.luasapp

import android.app.Application
import com.douglas.luasapp.di.AppComponent
import com.douglas.luasapp.di.DaggerAppComponent
import com.douglas.luasapp.di.module.HelperModule

class LuasApp: Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        component = DaggerAppComponent
            .builder()
            .helperModule(HelperModule(this))
            .build()
    }
}