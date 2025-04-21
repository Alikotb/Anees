package com.example.anees.ui.screens.qibla

import android.app.Activity
import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class QiblaViewModel(application: Application, private val activity: Activity) : AndroidViewModel(application), SensorEventListener {

    private val context = application.applicationContext
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val _azimuth = mutableStateOf(0f)
    val azimuth = _azimuth

    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

    private var qiblaDirection = 0f
    private var lastAzimuth = 0f
    private val alpha = 0.1f

    private val locationProvider = LocationProvider(context)

    init {
        registerSensor()
        locationProvider.fetchLatLong(activity) { location ->
            qiblaDirection = calculateQiblaAngle(location.latitude, location.longitude)
            Log.d("QIBLA", "Qibla direction: $qiblaDirection°")
        }
    }

    private fun registerSensor() {
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)?.also { rotationVectorSensor ->
            sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
            SensorManager.getOrientation(rotationMatrix, orientationAngles)

            val azimuthRad = orientationAngles[0]
            val azimuthDeg = Math.toDegrees(azimuthRad.toDouble()).toFloat()
            val deviceAzimuth = (azimuthDeg + 360) % 360

            val bearingToQibla = (qiblaDirection - deviceAzimuth + 360) % 360
            val smoothedAzimuth = lastAzimuth + alpha * (bearingToQibla - lastAzimuth)

            if (kotlin.math.abs(_azimuth.value - smoothedAzimuth) > 1f) {
                _azimuth.value = smoothedAzimuth
                lastAzimuth = smoothedAzimuth
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

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
