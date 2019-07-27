package com.douglas.luasapp.domain.helper

import android.content.Context
import android.net.ConnectivityManager
import com.douglas.luasapp.domain.helper.InternetConnectionHelper.Companion.INTERNET_STATUS_NO_CONNECTION
import com.douglas.luasapp.domain.helper.InternetConnectionHelper.Companion.INTERNET_STATUS_OK

class InternetConnectionHelperImpl(private val context: Context): InternetConnectionHelper {

    override fun checkInternetConnection(): Int {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return if (networkInfo != null && networkInfo.isConnected) {
            INTERNET_STATUS_OK
        } else {
            INTERNET_STATUS_NO_CONNECTION
        }
    }
}