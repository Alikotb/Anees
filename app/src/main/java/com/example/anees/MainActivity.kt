package com.example.anees

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.batoulapps.adhan.Coordinates
import com.example.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.example.anees.ui.navigation.SetUpNavHost
import com.example.anees.utils.extensions.requestNotificationPermission
import com.example.anees.utils.extensions.requestOverlayPermission
import com.example.anees.utils.extensions.setAllAlarms
import com.example.anees.utils.location.LocationProvider
import com.example.anees.utils.prayer_helper.PrayerTimesHelper
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    private var askedForOverlayPermission = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission(this)
        if (!Settings.canDrawOverlays(this)) {
            askedForOverlayPermission = true
            requestOverlayPermission()
        }
        else {
            val locationProvider = LocationProvider(this)
            locationProvider.fetchLatLong(this) { location ->
                Log.d("TAG", "onStart: ${location.latitude} ${location.longitude}")
                SharedPreferencesImpl(this).saveData("latitude", location.latitude)
                SharedPreferencesImpl(this).saveData("longitude", location.longitude)
                PrayerTimesHelper.coordinates = Coordinates(location.latitude, location.longitude)
                setAllAlarms()
            }

        }

        enableEdgeToEdge()
        setContent {
            val systemUiController = rememberSystemUiController()

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = Color.Transparent,
                    darkIcons = true
                )
            }
            navController = rememberNavController()
//            ScreenBackground()
            SetUpNavHost(navController = navController)


        }
    }

    override fun onResume() {
        super.onResume()
        if (askedForOverlayPermission && Settings.canDrawOverlays(this)) {
            askedForOverlayPermission = false
            val locationProvider = LocationProvider(this)
            locationProvider.fetchLatLong(this) { location ->
                Log.d("TAG", "onStart: ${location.latitude} ${location.longitude}")
                SharedPreferencesImpl(this).saveData("latitude", location.latitude)
                SharedPreferencesImpl(this).saveData("longitude", location.longitude)
                PrayerTimesHelper.coordinates = Coordinates(location.latitude, location.longitude)
                setAllAlarms()
            }
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








