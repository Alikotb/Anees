package com.example.anees.ui.screens.radio

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.anees.R
import com.example.anees.utils.extensions.isInternetAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RadioScreen(viewModel: RadioViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val snackbarMessage = remember { mutableStateOf<String?>(null) }

    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
    val currentStation by viewModel.currentStation.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularPulseVisualizer(isPlaying)
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF18181E)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(12.dp)
            ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .clickable { viewModel.previousStation() },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.back),
                                contentDescription = "Previous",
                                modifier = Modifier.size(64.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .clickable {
                                    if (context.isInternetAvailable()) {
                                        viewModel.playPauseRadio()
                                    } else {
                                        snackbarMessage.value = "لا يوجد اتصال بالإنترنت"
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = if (isPlaying) R.drawable.play else R.drawable.pause),
                                contentDescription = "Play/Pause",
                                modifier = Modifier.size(64.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .clickable { viewModel.nextStation() },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.right),
                                contentDescription = "Next",
                                modifier = Modifier.size(64.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = currentStation.name,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.othmani)),
                        modifier = Modifier.align(Alignment.End),
                        textAlign = TextAlign.Right
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = currentStation.description,
                        fontSize = 18.sp,
                        color = Color.LightGray,
                        fontFamily = FontFamily(Font(R.font.othmani)),
                        modifier = Modifier.align(Alignment.End),
                        textAlign = TextAlign.Right
                    )
                }
            }
        }
    }
    snackbarMessage.value?.let {
        CustomSnackbar(message = it) {
            snackbarMessage.value = null
        }
    }
}

@Composable
fun CircularPulseVisualizer(isPlaying: Boolean) {
    val infiniteTransition = rememberInfiniteTransition()

    val pulseAlpha1 by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing, delayMillis = 0),
            repeatMode = RepeatMode.Restart
        )
    )

    val pulseSize1 by infiniteTransition.animateFloat(
        initialValue = 80f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing, delayMillis = 0),
            repeatMode = RepeatMode.Restart
        )
    )

    val pulseAlpha2 by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing, delayMillis = 300),
            repeatMode = RepeatMode.Restart
        )
    )

    val pulseSize2 by infiniteTransition.animateFloat(
        initialValue = 80f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing, delayMillis = 300),
            repeatMode = RepeatMode.Restart
        )
    )

    val pulseAlpha3 by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing, delayMillis = 600),
            repeatMode = RepeatMode.Restart
        )
    )

    val pulseSize3 by infiniteTransition.animateFloat(
        initialValue = 80f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing, delayMillis = 600),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier.size(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.radio),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        if (isPlaying) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = Color(0xFF803F0B),
                    radius = pulseSize1,
                    alpha = pulseAlpha1
                )
                drawCircle(
                    color = Color(0x66FF6A00),
                    radius = pulseSize2,
                    alpha = pulseAlpha2
                )
                drawCircle(
                    color = Color(0xFFFFD580),
                    radius = pulseSize3,
                    alpha = pulseAlpha3
                )
            }
        }
    }
}

@Composable
fun CustomSnackbar(
    message: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF323232)),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )
        }
    }

    LaunchedEffect(message) {
        delay(2500)
        onDismiss()
    }
}





