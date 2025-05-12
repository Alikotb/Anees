package com.example.anees.utils.media_helper

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.anees.services.RadioService

object RadioServiceManager {
    fun startRadioService(context: Context, url: String, reciterUrl: String = "", index: Int = 0, reciterName: String = "", isRadio: Boolean = true) {
        val intent = Intent(context, RadioService::class.java).apply {
            putExtra("url", url)
            putExtra("isRadio", isRadio)
            putExtra("index", index)
            putExtra("reciterUrl", reciterUrl)
            putExtra("reciterName", reciterName)
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
}