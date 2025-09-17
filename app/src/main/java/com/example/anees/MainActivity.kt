package com.example.anees


import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import com.example.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.example.anees.receivers.AzanReminderReceiver
import com.example.anees.ui.navigation.SetUpNavHost
import com.example.anees.utils.Constants
import com.example.anees.utils.Constants.REQUEST_LOCATION_CODE
import com.example.anees.utils.SharedModel
import com.example.anees.utils.extensions.getCityAndCountryInArabic
import com.example.anees.utils.extensions.requestNotificationPermission
import com.example.anees.utils.extensions.requestOverlayPermission
import com.example.anees.utils.extensions.setAllAlarms
import com.example.anees.utils.location.LocationProvider
import com.example.anees.utils.location.checkPermission
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date
import kotlin.jvm.java
import kotlin.system.exitProcess
import kotlin.time.Duration.Companion.hours

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    private var askedForOverlayPermission = false
    lateinit var locationProvider: LocationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleNotification(this)
        SharedModel.isAppOpen = true
        requestNotificationPermission(this)
        locationProvider = LocationProvider(this)
        if (!Settings.canDrawOverlays(this)) {
            askedForOverlayPermission = true
            requestOverlayPermission()
        } else {
            if (this.checkPermission()) {
                val locationProvider = LocationProvider(this)
                locationProvider.fetchLatLong(this) { location ->
                    SharedPreferencesImpl(this).saveData("latitude", location.latitude)
                    SharedPreferencesImpl(this).saveData("longitude", location.longitude)
                    setAllAlarms()
                }
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    REQUEST_LOCATION_CODE
                )
            }

        }

        enableEdgeToEdge()
        setContent {

            var location by remember { mutableStateOf<Coordinates?>(null) }

            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setStatusBarColor(
                    color = Color.Transparent,
                    darkIcons = true
                )
            }
            if (checkPermission() && location == null) {
                if (location == null) {
                     locationProvider = LocationProvider(this)
                    locationProvider.fetchLatLong(this) { loc ->
                        location = Coordinates(loc.latitude, loc.longitude)
                        SharedPreferencesImpl(this).saveData("latitude", loc.latitude)
                        SharedPreferencesImpl(this).saveData("longitude", loc.longitude)
                    }
                }
            }
            navController = rememberNavController()
            SetUpNavHost(
                navController = navController,
                location = location?.let { getCityAndCountryInArabic(it.latitude, it.longitude) })
        }

    }

    override fun onResume() {
        super.onResume()
        SharedModel.isAppActive = true
        if (askedForOverlayPermission && Settings.canDrawOverlays(this)) {
            askedForOverlayPermission = false
            SharedPreferencesImpl(this).saveData(Constants.AZAN_NOTIFICATION_STATE, true)
             locationProvider = LocationProvider(this)
            locationProvider.fetchLatLong(this) { location ->
                SharedPreferencesImpl(this).saveData("latitude", location.latitude)
                SharedPreferencesImpl(this).saveData("longitude", location.longitude)
                setAllAlarms()
            }
        }

    }

    override fun onPause() {
        super.onPause()
        SharedModel.isAppActive = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!SharedModel.isAppActive) {
            finish()
            val activityManager = getSystemService(ACTIVITY_SERVICE) as android.app.ActivityManager
            val appTasks = activityManager.appTasks
            for (task in appTasks) {
                task.finishAndRemoveTask()
            }
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_LOCATION_CODE) {
            val prefs = getSharedPreferences("permission_prefs", MODE_PRIVATE)

            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                prefs.edit() { putInt("location_denial_count", 0)}
                locationProvider.fetchLatLong(this@MainActivity) { location ->
                    SharedPreferencesImpl(this@MainActivity).saveData("latitude", location.latitude)
                    SharedPreferencesImpl(this@MainActivity).saveData("longitude", location.longitude)
                    setAllAlarms()
                }
            } else {
                val currentCount = prefs.getInt("location_denial_count", 0)
                prefs.edit() { putInt("location_denial_count", currentCount + 1) }
                Toast.makeText(this, "Permission Denied (${currentCount + 1})", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val dateComponents = DateComponents.from(Date())

        val params = CalculationMethod.EGYPTIAN.parameters
        val prayerTimes = PrayerTimes(getCoordinates(context), dateComponents, params)

        //1- define time
        val times = listOf(
            prayerTimes.fajr,
            prayerTimes.dhuhr,
            prayerTimes.asr,
            prayerTimes.maghrib,
            prayerTimes.isha
        )

        //2- schedule
        for ((index, time) in times.withIndex()) {
            val intent = Intent(context, AzanReminderReceiver::class.java).apply {
                putExtra("soundType", index + 1) // sound type = 1..5
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                index,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, time.hours)
                set(Calendar.MINUTE, time.minutes )
                set(Calendar.SECOND, 0)
            }

            if (calendar.timeInMillis < System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1) // schedule tomorrow if already passed
            }

            calendar.timeInMillis -= 5* 60 * 1000

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    private fun getCoordinates(context: Context): Coordinates {
        val latitude = SharedPreferencesImpl(context).fetchData("latitude", 30.033333)
        val longitude = SharedPreferencesImpl(context).fetchData("longitude", 31.233334)
        return Coordinates(latitude, longitude)
    }
}







