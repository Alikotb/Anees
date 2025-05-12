package com.example.anees.utils.location


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.anees.utils.Constants.REQUEST_LOCATION_CODE


fun Context.checkPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}

fun Context.enableLocationService() {
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    this.startActivity(intent)
}

fun Context.isLocationEnabled(): Boolean {
    val locationManager = ContextCompat.getSystemService(this, LocationManager::class.java)
    return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true ||
            locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
}


fun Context.handleLocationPermission(activity: Activity?) {
    val prefs = getSharedPreferences("permission_prefs", Context.MODE_PRIVATE)
    val denialCount = prefs.getInt("location_denial_count", 0)

    when {
        checkPermission() -> {
            if (isLocationEnabled()) {
                Toast.makeText(this, "Permission Granted & Location Enabled", Toast.LENGTH_SHORT).show()
            } else {
                enableLocationService()
            }
        }

        denialCount >= 2 -> {
            AlertDialog.Builder(this)
                .setTitle("إذن الموقع مطلوب")
                .setMessage("يرجى الذهاب إلى إعدادات التطبيق > الأذونات وتمكين إذن الموقع لتعمل الميزة بشكل صحيح.")
                .setPositiveButton("فتح الإعدادات") { _, _ ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", packageName, null)
                    }
                    startActivity(intent)
                }
                .setNegativeButton("إلغاء", null)
                .show()

        }

        activity != null -> {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION_CODE
            )
        }

        else -> {
            Toast.makeText(this, "Activity is null, cannot request permission", Toast.LENGTH_SHORT).show()
        }
    }
}
