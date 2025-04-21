package com.example.anees.ui.screens.qibla

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
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
    val viewModel: QiblaViewModel = viewModel(
        factory = QiblaViewModelFactory(context.applicationContext as Application, activity)
    )

    val rotationAngle by viewModel.azimuth
    Log.d("QIBLA", "Rotation angle to Qibla: $rotationAngle")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.qibla),
            contentDescription = "Qibla Direction",
            modifier = Modifier
                .size(250.dp)
                .rotate(rotationAngle)
        )
    }
}
