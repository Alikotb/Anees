package com.example.anees.ui.screens.radio.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.anees.R

@Composable
fun RadioBackground() {
    Image(
        painter = painterResource(id = R.drawable.zekrback),
        contentDescription = "Background",
        modifier = Modifier.fillMaxSize().alpha(0.22f),
        contentScale = ContentScale.Crop
    )
}