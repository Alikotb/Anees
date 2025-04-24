package com.example.anees

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.anees.ui.screens.qibla.LocationProvider
import com.example.anees.ui.screens.qibla.QiblaScreen
import com.example.anees.ui.screens.quran.QuranIndexScreen
import com.example.anees.ui.screens.quran.QuranPDFViewerScreen
import com.example.anees.ui.theme.AneesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//enableEdgeToEdge()
        setContent {
            QuranPDFViewerScreen()
           // QuranIndexScreen()
        }
    }

    override fun onStart() {
        super.onStart()
        val locationProvider = LocationProvider(this)
        locationProvider.fetchLatLong(this) { location ->
            Log.d("TAG", "Latitude: ${location.latitude}, Longitude: ${location.longitude}")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("TAG", "onRequestPermissionsResult: called")

        val locationProvider = LocationProvider(this)
        locationProvider.handlePermissionResult(requestCode, grantResults, this) {
        }
    }
}


