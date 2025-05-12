package com.example.anees

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.batoulapps.adhan.Coordinates
import com.example.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.example.anees.ui.navigation.SetUpNavHost
import com.example.anees.ui.screens.azan.AzanOverlayActivity
import com.example.anees.utils.SharedModel
import com.example.anees.utils.extensions.getCityAndCountryInArabic
import com.example.anees.utils.extensions.requestNotificationPermission
import com.example.anees.utils.extensions.requestOverlayPermission
import com.example.anees.utils.extensions.setAllAlarms
import com.example.anees.utils.location.LocationProvider
import com.example.anees.utils.prayer_helper.PrayerTimesHelper
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    private var askedForOverlayPermission = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedModel.isAppOpen = true
        requestNotificationPermission(this)
        if (!Settings.canDrawOverlays(this)) {
            askedForOverlayPermission = true
            requestOverlayPermission()
        }
        else {
            val locationProvider = LocationProvider(this)
            locationProvider.fetchLatLong(this) { location ->
                SharedPreferencesImpl(this).saveData("latitude", location.latitude)
                SharedPreferencesImpl(this).saveData("longitude", location.longitude)
                setAllAlarms()
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

            if (location == null) {
                val locationProvider = LocationProvider(this)
                locationProvider.fetchLatLong(this) { loc ->
                    location = Coordinates(loc.latitude, loc.longitude)
                    SharedPreferencesImpl(this).saveData("latitude", loc.latitude)
                    SharedPreferencesImpl(this).saveData("longitude", loc.longitude)
                }
            }

            navController = rememberNavController()
            SetUpNavHost(navController = navController,
                location = location?.let { getCityAndCountryInArabic(it.latitude, it.longitude) })
        }

    }

    override fun onResume() {
        super.onResume()
        SharedModel.isAppActive = true
        if (askedForOverlayPermission && Settings.canDrawOverlays(this)) {
            askedForOverlayPermission = false
            val locationProvider = LocationProvider(this)
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
        val locationProvider = LocationProvider(this)
        locationProvider.handlePermissionResult(requestCode, grantResults, this) {
        }
    }
}







