package com.example.movieapplication.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtil {
    const val strHourMinuteSecond = "HH:mm:ss"
    private const val strDefaultFormat = "yyyy-MM-dd'T'HH:mm:ss"

    @SuppressLint("ConstantLocale")
    val hourMinuteSecondFormat = SimpleDateFormat(strHourMinuteSecond, Locale.US)

    @SuppressLint("ConstantLocale")
    private val defaultDateFormat = SimpleDateFormat(strDefaultFormat, Locale.US)


    fun getDateFromString(dateString: String) : Date {
        return defaultDateFormat.parse(dateString)!!
    }

    fun getDateToString(date: Date): String {
        return defaultDateFormat.format(date)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}