package com.example.anees.utils.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.anees.enums.PrayEnum
import com.example.anees.receivers.AzanAlarmReceiver
import com.example.anees.utils.prayer_helper.PrayerTimesHelper
import com.example.anees.utils.reminder_notification.mapPrayEnumToNumber
import com.example.anees.workers.AlarmResetWorker
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

fun Context.isInternetAvailable(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

fun Context.cancelAllAlarms() {
    var index = 0
    for (pray in PrayerTimesHelper.getUpcomingPrayers()) {
        val intent = Intent(this, AzanAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            index++,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}

fun Context.setAllAlarms(
){
   // cancelAllAlarms()
    var index = 0;
    for (pray in PrayerTimesHelper.getUpcomingPrayers()) {
        setAlarm(
            requestCode = index++,
            prayEnum = pray.first,
            triggerTimeMillis = pray.second
        )
    }
}


@SuppressLint("ScheduleExactAlarm")
fun Context.setAlarm(
    requestCode: Int = 0,
    triggerTimeMillis: Long,
    prayEnum: PrayEnum
) {
    Log.e("TAG1", "setAlarm:$requestCode : ${prayEnum.value} -> ${triggerTimeMillis.toArabicTime().convertNumbersToArabic()}", )
    val intent = Intent(this, AzanAlarmReceiver::class.java)
    intent.putExtra("prayEnum", prayEnum)
    intent.putExtra("time", triggerTimeMillis)
    val reminderNotificationIntent = Intent(this, AzanAlarmReceiver::class.java)
    val prayCode = mapPrayEnumToNumber(prayEnum.value)
    Log.i("TAG", "setAlarm: $prayCode")
    reminderNotificationIntent.putExtra("soundType", prayCode)

    val pendingIntent = PendingIntent.getBroadcast(
        this,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val reminderNotificationPendingIntent = PendingIntent.getBroadcast(
        this,
        requestCode+5,
        reminderNotificationIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            startActivity(intent)
        }
    }
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        triggerTimeMillis,
        pendingIntent
    )

    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        triggerTimeMillis - (5 * 60 * 1000),
        reminderNotificationPendingIntent
    )
}
fun Context.isVolumeZero(): Boolean {
    val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    return volume == 0
}

fun Context.requestOverlayPermission() {
    if (!Settings.canDrawOverlays(this)) {
        try {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } catch (e: Exception) {
            val fallbackIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            fallbackIntent.data = Uri.parse("package:$packageName")
            fallbackIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(fallbackIntent)
        }
    }
}

fun Context.scheduleMidnightAlarmReset() {
    val now = Calendar.getInstance()
    val midnight = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        if (before(now)) {
            add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    val initialDelay = midnight.timeInMillis - now.timeInMillis

    val workRequest = PeriodicWorkRequestBuilder<AlarmResetWorker>(1, TimeUnit.DAYS)
        .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
        .addTag("AlarmResetWorker")
        .build()

    WorkManager.getInstance(this).enqueueUniquePeriodicWork(
        "DailyAlarmReset",
        ExistingPeriodicWorkPolicy.UPDATE,
        workRequest
    )
}
fun Context.getCityAndCountryInArabic(lat: Double, lon: Double): String {
    return try {
        val geocoder = Geocoder(this, Locale("ar"))
        val addresses = geocoder.getFromLocation(lat, lon, 1)

        if (!addresses.isNullOrEmpty()) {
            val city = addresses[0].subAdminArea ?: addresses[0].subAdminArea
            val country = addresses[0].countryName

            Log.d("TAG", "getCityAndCountryInArabic: ${addresses[0]}")
            Log.d("TAG", "getCityAndCountryInArabic: ${addresses[0].subAdminArea} ${addresses[0].adminArea}")
            listOfNotNull(city, country).joinToString("، ")


        } else {
            "غير معروف"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "غير معروف"
    }
}

fun Context.requestNotificationPermission(activity: Activity, requestCode: Int = 1001) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                requestCode
            )
        }
    }
}





