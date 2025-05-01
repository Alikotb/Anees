package com.example.anees.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.anees.ui.screens.azan.AzanOverlayActivity

class AzanAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        val overlayIntent = Intent(context, AzanOverlayActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(overlayIntent)

    }
}
