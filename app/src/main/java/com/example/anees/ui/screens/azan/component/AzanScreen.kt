package com.example.anees.ui.screens.azan.component

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.R
import com.example.anees.enums.PrayEnum
import com.example.anees.utils.prayer_helper.PrayerTimesHelper
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Preview
@Composable
fun AzanScreen(
    prayEnum : PrayEnum  = PrayEnum.MAGHRIB,
    prayerTime: String = "5:37 ص",
    onStopClicked: () -> Unit = {},
) {

    val context = LocalContext.current

    val mediaPlayer = remember { MediaPlayer.create(context, if(prayEnum== PrayEnum.FAJR) R.raw.azan_fajr else R.raw.azan2) }
    val isPlaying = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        mediaPlayer.setOnCompletionListener {
            isPlaying.value = false
            onStopClicked()
        }
        mediaPlayer.start()
        isPlaying.value = true
    }

    DisposableEffect(Unit) {
        onDispose {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            mediaPlayer.release()
        }
    }


    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false
        )
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {


        Scaffold(topBar = {}
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = prayEnum.azanBackground),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 40.dp)
                        .size(32.dp)
                        .background(
                            Color.DarkGray.copy(alpha = 0.4f), shape = RoundedCornerShape(50.dp)
                        ).padding(4.dp).clickable {
                            if (mediaPlayer.isPlaying) {
                                // mediaPlayer.stop()
                                //mediaPlayer.release()
                            }
                            onStopClicked()
                        }

                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier.padding(top = 64.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AzanPulseView(
                            prayerName = if (PrayerTimesHelper.isTodayFriday() && prayEnum == PrayEnum.ZUHR ) "صلاة الجمعة" else prayEnum.value
                            , prayerTime = prayerTime,
                            isDay = if (prayEnum == PrayEnum.ZUHR || prayEnum == PrayEnum.ASR) true else false
                        )

                    }
                    Column {
                        Text(
                            text = "${prayEnum.hadith}",
                            color = Color.White, fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(
                                Font(R.font.othmani)
                            ), modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "${prayEnum.author}",
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily(Font(R.font.othmani)),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }


                    Text(
                        text = "أنــيــس الـمــســلــم",
                        color = Color.White, fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.othmani))
                    )
                }
            }
        }

    }
}
