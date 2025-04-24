package com.example.anees.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.anees.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navToHome: () -> Unit
) {
    LaunchedEffect(true) {
        delay(2500)
        navToHome()
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.white)
            )
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.size(300.dp)
        )
    }
}