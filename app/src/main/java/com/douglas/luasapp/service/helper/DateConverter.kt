package com.douglas.luasapp.service.helper

import com.tickaroo.tikxml.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter: TypeConverter<Date> {

    companion object {
        const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
    }

    private val formatter = SimpleDateFormat(DATE_PATTERN, Locale.ROOT)

    override fun write(value: Date?): String {

        return value?.let {

            formatter.format(it)

        }?: ""
    }

    override fun read(value: String?): Date {

        return value?.let {

            formatter.parse(it)

        }?: Date()
    }
}