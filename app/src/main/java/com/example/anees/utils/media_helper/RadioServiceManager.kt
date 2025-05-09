package com.example.anees.utils.media_helper

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.anees.services.RadioService

object RadioServiceManager {
    fun startRadioService(context: Context, url: String) {
        val intent = Intent(context, RadioService::class.java).apply {
            putExtra("url", url)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        }else {
            context.startService(intent)
        }
    }

    fun sendRadioAction(context: Context, action: String) {
        val intent = Intent(context, RadioService::class.java).apply {
            this.action = action
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    fun stopRadioService(context: Context) {
        val intent = Intent(context, RadioService::class.java)
        context.stopService(intent)
    }

    fun isServiceRunning(context: Context): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(Integer.MAX_VALUE)
            .any { it.service.className == RadioService::class.java.name }
    }
}