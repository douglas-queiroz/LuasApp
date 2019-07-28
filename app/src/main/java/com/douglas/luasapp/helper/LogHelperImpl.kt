package com.douglas.luasapp.helper

class LogHelperImpl: LogHelper {

    override fun logError(error: Throwable) {
        error.printStackTrace()
    }
}