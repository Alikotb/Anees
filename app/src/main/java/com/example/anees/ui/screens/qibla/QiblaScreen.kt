package com.example.anees.ui.screens.qibla

import android.app.Activity

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anees.R

@Preview(showBackground = true)
@Composable
fun QiblaScreen() {
    val context = LocalContext.current
    val activity = context as Activity
    val viewModel: QiblaViewModel = viewModel()

    LaunchedEffect(Unit) {
        LocationProvider(context).fetchLatLong(activity) { location ->
            viewModel.updateQiblaDirection(location.latitude, location.longitude)
        }
    }

    val deviceAzimuth by viewModel.deviceAzimuth
    val bearingToQibla by viewModel.bearingToQibla

    val animatedDeviceRotation by animateFloatAsState(
        targetValue = deviceAzimuth,
        animationSpec = tween(300)
    )

    val animatedQiblaRotation by animateFloatAsState(
        targetValue = bearingToQibla,
        animationSpec = tween(300)
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF272727)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.compassbg),
            contentDescription = "Qibla Compass Background",
            modifier = Modifier
                .background(color = Color(0xFF272727))
                .align(Alignment.Center)
        )

        Image(
            painter = painterResource(id = R.drawable.qiblaa),
            contentDescription = "Qibla Compass Needle",
            modifier = Modifier
                .rotate(animatedDeviceRotation)
                .align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
                .rotate(animatedQiblaRotation)

        ) {
            Image(
                painter = painterResource(id = R.drawable.kaaba),
                contentDescription = "Kaaba Direction",
                modifier = Modifier
                    .size(32.dp)
                    .padding(top = 8.dp)
                    .align(Alignment.TopCenter)
            )
        }
    }
}
