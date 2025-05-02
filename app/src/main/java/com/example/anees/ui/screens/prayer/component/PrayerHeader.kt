package com.example.anees.ui.screens.prayer.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.anees.enums.PrayEnum
import com.example.anees.utils.extensions.convertNumbersToArabic
import com.example.anees.utils.extensions.toArabicTime
import com.example.anees.utils.prayer_helper.PrayerTimesHelper
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
private fun HeaderSection(
    prayEnum: PrayEnum,
    remainingTime: String,
    targetTime : String
) {
    val textColor = Color(0xFF3B3B3B)
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
           text =  "${prayEnum.value}", fontSize = 24.sp,
            color = textColor, textAlign = TextAlign.Center,
        )
        Text("${targetTime}", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = textColor)
        Text("الصلاة القادمة بعد $remainingTime".convertNumbersToArabic(), fontSize = 14.sp, color = textColor)
    }
}

@Composable
fun HeaderWithTimer() {
    var remainingTime by remember { mutableStateOf("") }

    val (prayEnum, targetTime) = PrayerTimesHelper.getNextPrayer()!!

    LaunchedEffect(targetTime) {
        while (true) {
            val diff = targetTime - System.currentTimeMillis()
            if (diff <= 0) {
                remainingTime = "00:00:00"
                break
            }
            val hours = TimeUnit.MILLISECONDS.toHours(diff)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(diff) % 60
            remainingTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            delay(1000L)
        }
    }

    HeaderSection(
        prayEnum = prayEnum,
        remainingTime = remainingTime,
        targetTime = targetTime.toArabicTime().convertNumbersToArabic()
    )
}