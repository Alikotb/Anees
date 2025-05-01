package com.example.anees

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.anees.ui.navigation.SetUpNavHost
import com.example.anees.utils.extensions.setAlarm
import com.example.anees.utils.location.LocationProvider
import com.example.anees.utils.prayer_helper.PrayerTimesHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.component1
import kotlin.collections.component2

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            SetUpNavHost(navController = navController)

            val tenSecondsLater = System.currentTimeMillis() + 5_000
            setAlarm(tenSecondsLater)


        }
    }

    override fun onStart() {
        super.onStart()
        val locationProvider = LocationProvider(this)
        locationProvider.fetchLatLong(this) { location ->
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val locationProvider = LocationProvider(this)
        locationProvider.handlePermissionResult(requestCode, grantResults, this) {
        }
    }
}


