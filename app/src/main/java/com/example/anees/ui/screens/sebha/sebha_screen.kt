package com.example.anees.ui.screens.sebha

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import com.example.anees.utils.azkarList

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.anees.R
import com.example.anees.data.model.Sebiha
import com.example.anees.ui.screens.sebha.component.ZekrHorizontalStaggeredGrid
import kotlinx.coroutines.delay


@Composable
fun SebihaScreen(
    navToHome: () -> Unit = {}
) {
    val viewModel: SebihaViewModel = hiltViewModel()
    val sebiha = viewModel.sebiha.collectAsState()
    var currentIndex by remember { mutableIntStateOf(0) }
    var counter = sebiha.value.count
    var rounds = sebiha.value.rounds
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.celebration))
    var isLottieVisible by remember { mutableStateOf(false) }

    LaunchedEffect(rounds) {
        if (rounds % 2 == 0 && rounds != 0) {
            isLottieVisible = true
            delay(4000)
            isLottieVisible = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F0E1), Color(0xFFEAE3D2))
                )
            )
            .padding(16.dp)
    ) {
        if (isLottieVisible) {
            LottieAnimation(
                composition = composition.value,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.fillMaxSize(),
                renderMode = RenderMode.AUTOMATIC
            )
        }
        Box {
            IconButton(
                onClick = {
                    navToHome()
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 24.dp, start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = azkarList[currentIndex].arabicName,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4E342E),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        counter = 0
                        rounds = 0
                        viewModel.addSebiha(Sebiha(0, counter, rounds))
                    },
                    modifier = Modifier
                        .offset(y = 40.dp)
                        .size(48.dp)
                        .background(Color.Red, shape = CircleShape)
                ) {
                    Icon(Icons.Default.Close, contentDescription = null, tint = Color.White)
                }
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier.size(250.dp)
                ) {
                    Card(
                        shape = CircleShape,
                        modifier = Modifier.size(250.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = counter.toString(),
                                fontSize = 48.sp,
                                color = Color.Green,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            counter++
                            if (counter >= 33) {
                                counter = 0
                                rounds++
                            }
                            viewModel.addSebiha(Sebiha(0, counter, rounds))
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = 40.dp)
                            .size(80.dp)
                            .background(Color(0xFF4CAF50), shape = CircleShape)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                    }
                }

                IconButton(
                    onClick = {
                        counter = 0
                        viewModel.addSebiha(Sebiha(0, counter, rounds))
                    },
                    modifier = Modifier
                        .offset(y = 40.dp)
                        .size(48.dp)
                        .background(Color(0xFF4CAF50), shape = CircleShape)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "الدورات: $rounds",
                color = Color(0xFF6D4C41),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 32.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            ZekrHorizontalStaggeredGrid(
                currentIndex,
                onZekrClick = {
                    currentIndex = it
                    counter = 0
                    rounds = 0
                    viewModel.addSebiha(Sebiha(0, counter, rounds))
                },
            )
        }

    }
}

