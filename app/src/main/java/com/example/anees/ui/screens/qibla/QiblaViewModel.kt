package com.example.anees.ui.screens.qibla

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class QiblaViewModel(application: Application, private val activity: Activity) : AndroidViewModel(application), SensorEventListener {

    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)
    private val gravity = FloatArray(3)
    private val geomagnetic = FloatArray(3)

    private val _azimuth = mutableStateOf(0f)
    val azimuth = _azimuth

    private val locationProvider = LocationProvider(context)
    private var qiblaDirection = 0f

    init {
        registerSensors()

        // Fetch Qibla direction based on location
        locationProvider.fetchLatLong(activity) { location ->
            qiblaDirection = calculateQiblaAngle(location.latitude, location.longitude)
            Log.d("QIBLA", "Qibla direction: ${location.latitude}, ${location.longitude}")
        }
    }

    private fun registerSensors() {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        }

        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also { magneticField ->
            sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                System.arraycopy(event.values, 0, gravity, 0, gravity.size)
            }
            Sensor.TYPE_MAGNETIC_FIELD -> {
                System.arraycopy(event.values, 0, geomagnetic, 0, geomagnetic.size)
            }
        }

        if (SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic)) {
            SensorManager.getOrientation(rotationMatrix, orientationAngles)
            val azimuthInRadians = orientationAngles[0]
            val azimuthInDegrees = Math.toDegrees(azimuthInRadians.toDouble()).toFloat()
            val deviceAzimuth = (azimuthInDegrees + 360) % 360

            // Calculate rotation needed
            val bearingToQibla = (qiblaDirection - deviceAzimuth + 360) % 360
            _azimuth.value = bearingToQibla
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Can be ignored unless needed
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }

    private fun calculateQiblaAngle(lat: Double, lon: Double): Float {
        val kaabaLat = Math.toRadians(21.4225)
        val kaabaLon = Math.toRadians(39.8262)

        val userLat = Math.toRadians(lat)
        val userLon = Math.toRadians(lon)

        val deltaLon = kaabaLon - userLon

        val y = sin(deltaLon) * cos(kaabaLat)
        val x = cos(userLat) * sin(kaabaLat) - sin(userLat) * cos(kaabaLat) * cos(deltaLon)

        val angle = Math.toDegrees(atan2(y, x))
        return ((angle + 360) % 360).toFloat()
    }
}



class QiblaViewModelFactory(
    private val context: Application,
    private val activity: Activity
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QiblaViewModel(context, activity) as T
    }
}

