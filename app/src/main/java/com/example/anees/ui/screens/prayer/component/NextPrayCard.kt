package com.example.anees.ui.screens.prayer.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.enums.PrayEnum
import com.example.anees.utils.prayer_helper.PrayerTimesHelper
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun PrayerCard(
    prayer: PrayEnum,
    city: String,
    country: String,
    remainingTime: String,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Card(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = onCardClick
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = prayer.icon),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = prayer.value,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = remainingTime,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "$city، $country",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}



@Composable
fun PrayerCardWithTimer(
    city: String = "زفتي",
    country: String = "مصر",
    onCardClick: () -> Unit
) {
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

    PrayerCard(
        prayer = prayEnum,
        city = city,
        country = country,
        remainingTime = remainingTime,
        onCardClick = onCardClick
    )
}
