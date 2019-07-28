package com.douglas.luasapp.helper

import java.util.*

class TimeHelperImpl: TimeHelper {

    override fun getCurrentHour(): Int {

        val calendar = GregorianCalendar.getInstance()

        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    override fun getCurrentMin(): Int {

        val calendar = GregorianCalendar.getInstance()

        return calendar.get(Calendar.MINUTE)
    }
}