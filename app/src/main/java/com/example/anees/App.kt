package com.example.anees

import android.app.Application
import com.abdok.atmosphere.data.local.sharedPreference.SharedPreferencesImpl
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
       // SharedPreferencesImpl.initSharedPreferences(this)

    }
}