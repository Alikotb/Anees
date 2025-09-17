package com.example.anees.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.anees.MainActivity

class AzanReminderBootReceiver: BroadcastReceiver()  {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Re-schedule your alarms
            val mainActivity = MainActivity()
            mainActivity.scheduleNotification(context)
        }
    }
}