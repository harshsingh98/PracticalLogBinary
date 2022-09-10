package com.example.practicallogbinary.utils

import android.content.Context
import android.widget.Toast
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

object ExtFuncs {
    fun Context.notifyUser(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, length).show()
    }

    //to get converted date from timestamp
    fun String.convertedDateTimestamp(dateFormat: String): String {
        try {
            val sdf = SimpleDateFormat(dateFormat)
            val netDate = Date(this.toLong()/* * 1000*/)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    //to check number is prime or not
    fun Int.checkNonPrimeOrPrime(): Boolean{
        var flag = false
        for (i in 2..this / 2) {
            if (this % i == 0) {
                flag = true
                break
            }
        }
        return flag
    }

    //for logs
    fun logD(tag: String = "LOG_BINARY_APP", message: String) {
        Timber.tag(tag).d(message)
    }

    fun logE(tag: String = "LOG_BINARY_APP", message: String) {
        Timber.tag(tag).e(message)
    }

    fun logI(tag: String = "LOG_BINARY_APP", message: String = "") {
        Timber.tag(tag).i(message)
    }
}