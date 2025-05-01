package com.example.anees.utils.extensions

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.annotation.RequiresPermission
import com.example.anees.receivers.AzanAlarmReceiver

fun Context.isInternetAvailable(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}


@SuppressLint("ScheduleExactAlarm")
fun Context.setAlarm(
    triggerTimeMillis: Long,
    requestCode: Int = 0
) {
    val intent = Intent(this, AzanAlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        this,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        triggerTimeMillis,
        pendingIntent
    )
}
