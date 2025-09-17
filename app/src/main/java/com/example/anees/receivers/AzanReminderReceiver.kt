package com.example.anees.receivers

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.anees.R
import kotlin.jvm.java
import androidx.core.net.toUri
import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import com.example.anees.data.local.sharedpreference.SharedPreferencesImpl
import java.util.Date

class AzanReminderReceiver: BroadcastReceiver()  {
    @SuppressLint("ScheduleExactAlarm")
    override fun onReceive(context: Context?, intent: Intent?) {
        val soundType = intent?.getIntExtra("soundType", 0)

        //1- channel
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "azan_reminder_notification$soundType"
        val azan = when (soundType) {
            1 -> "Fajr"
            2 -> "Zuhr"
            3 -> "Asr"
            4 -> "Maghreb"
            5 -> "Isha"
            else -> "Unknown"
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //2- pick sound
            val soundUri = getNotificationSound(soundType!!, context)

            val channel = NotificationChannel(
                channelId,
                azan,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setSound(soundUri, null) // set custom sound here
            }

            notificationManager.createNotificationChannel(channel)
        }

        val notificationIcon = getNotificationIcon(soundType!!, context)

        //3- build notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(azan)
            .setContentText("It's time to pray")
            .setSmallIcon(notificationIcon)
            .build()

        notificationManager.notify(soundType, notification)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val newIntent = Intent(context, AzanReminderReceiver::class.java).apply {
            putExtra("soundType", soundType)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            soundType, // same requestCode as before
            newIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val dateComponents = DateComponents.from(Date())

        val params = CalculationMethod.EGYPTIAN.parameters
        val prayerTimes = PrayerTimes(getCoordinates(context), dateComponents, params)

        val calendar = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1) // tomorrow
            when (soundType) {
                1 -> { prayerTimes.fajr.minutes  }
                2 -> { prayerTimes.dhuhr.minutes }
                3 -> { prayerTimes.asr.minutes  }
                4 -> { prayerTimes.maghrib.minutes }
                5 -> { prayerTimes.isha.minutes  }
            }
            set(Calendar.SECOND, 0)
        }

        calendar.timeInMillis -= 5 * 60 * 1000

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    private fun getNotificationSound(ind:Int,context: Context) : Uri {
        return when (ind) {
            1 -> "android.resource://${context.packageName}/${R.raw.fajr}".toUri()
            2 -> "android.resource://${context.packageName}/${R.raw.duhr}".toUri()
            3 -> "android.resource://${context.packageName}/${R.raw.asr}".toUri()
            4 -> "android.resource://${context.packageName}/${R.raw.maghreb}".toUri()
            5 -> "android.resource://${context.packageName}/${R.raw.isha}".toUri()
            else -> RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
    }

    private fun getNotificationIcon(ind: Int,context: Context): Int{
        return when (ind) {
            1 -> R.drawable.fajr
            2 -> R.drawable.dhuhr
            3 -> R.drawable.asr
            4 -> R.drawable.maghrib
            5 -> R.drawable.isha
            else -> R.drawable.maghrib
        }
    }

    private fun getCoordinates(context: Context): Coordinates {
        val latitude = SharedPreferencesImpl(context).fetchData("latitude", 30.033333)
        val longitude = SharedPreferencesImpl(context).fetchData("longitude", 31.233334)
        return Coordinates(latitude, longitude)
    }
}