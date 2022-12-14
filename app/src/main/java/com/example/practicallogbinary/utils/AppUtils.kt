package com.example.practicallogbinary.utils

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {
    fun dp2Px(dp: Int, context: Context): Int {
        return (context.resources.displayMetrics.density * dp).toInt()
    }

    fun px2Dp(dp: Int, context: Context): Int {
        return (context.resources.displayMetrics.density / dp).toInt()
    }

    fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return false
    }

    fun convertDateFormat(date: String, inputFormat: String, outputFormat: String): String {
        val input = SimpleDateFormat(inputFormat, Locale.US)
        val output = SimpleDateFormat(outputFormat, Locale.US)
        val formattedDate = input.parse(date)
        return output.format(formattedDate!!)
    }

    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}