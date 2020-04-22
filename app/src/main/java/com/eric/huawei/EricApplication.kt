package com.eric.huawei

import android.app.Application
import timber.log.Timber

class EricApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}