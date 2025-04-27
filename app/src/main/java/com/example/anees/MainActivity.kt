package com.example.anees

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.anees.ui.navigation.SetUpNavHost
import com.example.anees.utils.location.LocationProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            SetUpNavHost(navController = navController)

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


