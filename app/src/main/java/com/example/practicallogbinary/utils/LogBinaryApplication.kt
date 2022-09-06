package com.example.practicallogbinary.utils

import android.app.Application
import com.example.practicallogbinary.BuildConfig
import timber.log.Timber

class LogBinaryApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}