package com.example.anees.utils.extensions

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.example.anees.enums.PrayEnum
import com.example.anees.receivers.AzanAlarmReceiver
import com.example.anees.utils.prayer_helper.PrayerTimesHelper

fun Context.isInternetAvailable(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}


@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("ScheduleExactAlarm")
fun Context.setAlarm(
    requestCode: Int = 0
) {
    val (prayEnum , triggerTimeMillis) = PrayerTimesHelper.getNextPrayer() ?: return



    val intent = Intent(this, AzanAlarmReceiver::class.java)
    intent.putExtra("prayEnum" , prayEnum)
    intent.putExtra("time" , triggerTimeMillis)

    val pendingIntent = PendingIntent.getBroadcast(
        this,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )


    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    if (!alarmManager.canScheduleExactAlarms()) {
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        startActivity(intent)
    }
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        triggerTimeMillis,
        pendingIntent
    )
fun Context.isVolumeZero(): Boolean {
    val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    return volume == 0
}
