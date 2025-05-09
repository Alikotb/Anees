package com.example.anees.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.anees.enums.PrayEnum
import com.example.anees.ui.screens.azan.AzanOverlayActivity

class AzanAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        val prayEnum = intent?.getSerializableExtra("prayEnum") as? PrayEnum
        val time = intent?.getLongExtra("time" , 0)
        val overlayIntent = Intent(context, AzanOverlayActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("prayEnum" , prayEnum)
            putExtra("time" , time)
        }
        context.startActivity(overlayIntent)
    }
}
