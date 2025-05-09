package com.example.anees

import android.app.Application
import com.example.anees.utils.extensions.scheduleMidnightAlarmReset
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
       // SharedPreferencesImpl.initSharedPreferences(this)
        scheduleMidnightAlarmReset()
    }
}