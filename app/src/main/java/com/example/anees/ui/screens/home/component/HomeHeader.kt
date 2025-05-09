package com.example.anees.ui.screens.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.R
import com.example.anees.enums.PrayEnum
import com.example.anees.utils.extensions.convertNumbersToArabic
import com.example.anees.utils.extensions.toArabicTime
import com.example.anees.utils.prayer_helper.PrayerTimesHelper
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit


@Preview
@Composable
fun HomeHeader(
    hijriDate: String = "24 رمضان 1445 هـ".convertNumbersToArabic(),
    location: String = "القاهرة، مصر",
    prayerName: String = "صلاة الظهر",
    prayerTime: String = "12:45 م".convertNumbersToArabic(),
    remainingTime: String = "5:02:02".convertNumbersToArabic(),
    onCardClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp).padding(top = 4.dp)
    )
    {

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = location,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = hijriDate,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF803F0B),
                            Color(0xFF5A2E0E),
                            Color(0xFF311403)
                        )
                    )
                )
                .padding(16.dp)
                .clickable(
                    onClick = onCardClick
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.l),
                contentDescription = null,
                modifier = Modifier.matchParentSize()
            )
            // العنوان أعلى اليمين
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "الصلاة القادمة",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                    )
                }


                // التفاصيل الرئيسية في المنتصف
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
/*                    Image(
                        painter = painterResource(id = R.drawable.dhuhr), // استبدل بالأيقونة المناسبة
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )*/
                    Spacer(modifier = Modifier.height(8.dp))
                    ExtrudedText(
                        text = prayerName,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = prayerTime,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            Color.White.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .align(Alignment.Start)
                ) {
                    Text(
                        text = "الصلاة القادمة خلال :  ${remainingTime}".convertNumbersToArabic(),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ExtrudedText(
    text: String = "صلاة الظهر",
    fontSize: TextUnit = 26.sp,
    frontColor: Color = Color.White,
    shadowColor: Color = Color.Black.copy(alpha = 0.6f),
    offsetDp: Dp = 3.dp
) {
    Box {
        // ظل Text
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = shadowColor,
            modifier = Modifier
                .offset(x = offsetDp, y = offsetDp)
        )
        // النص الأمامي
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = frontColor
        )
    }
}

@Composable
fun PrayerCardWithTimer(
    location  : String = "القاهرة، مصر",
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


    HomeHeader(
        hijriDate = "24 رمضان 1445 هـ".convertNumbersToArabic(),
        location = location,
        prayerName = if (PrayerTimesHelper.isTodayFriday() && prayEnum == PrayEnum.ZUHR ) "صلاة الجمعة" else prayEnum.value,
        prayerTime = targetTime.toArabicTime().convertNumbersToArabic(),
        remainingTime = remainingTime.convertNumbersToArabic()
    ){
        onCardClick()
    }
}


