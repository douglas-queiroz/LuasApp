package com.douglas.luasapp.domain.helper

interface InternetConnectionHelper {

    companion object {
        const val INTERNET_STATUS_OK = 0
        const val INTERNET_STATUS_NO_CONNECTION = 1
    }

    fun checkInternetConnection(): Int
}