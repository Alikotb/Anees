package com.example.anees.ui.screens.prayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.anees.R
import com.example.anees.ui.screens.prayer.component.HeaderWithTimer
import com.example.anees.utils.extensions.convertNumbersToArabic
import com.example.anees.utils.prayer_helper.PrayerTimesHelper


@Preview(showBackground = true)
@Composable
fun PrayerScreen(){
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(
            Modifier.fillMaxSize()
        ){
            Image(
                painter = painterResource(id = R.drawable.zekrback),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize().alpha(.22f),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(text = "زفتي , مصر", fontSize = 14.sp, color = Color.Black,
                        modifier = Modifier.background(Color.LightGray.copy(alpha = 0.6f) , shape = RoundedCornerShape(50.dp))
                            .padding(vertical = 4.dp , horizontal = 8.dp))
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(48.dp),
                    ){
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Back")
                    }

                }

                HeaderWithTimer()
                Spacer(modifier = Modifier.height(32.dp))
                DateSection("16 شوال 1446 هـ".convertNumbersToArabic())
                Spacer(modifier = Modifier.height(8.dp))
                PrayerList()
            }
        }
    }
}
@Composable
fun DateSection(hijriDate: String) {
    Row(
        modifier = Modifier.background(Color.Transparent).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(hijriDate, fontWeight = FontWeight.Bold)
    }
}
@Composable
fun PrayerList() {
    val prayers = PrayerTimesHelper.getAllPrayers()

    Column {
        prayers.forEach { (prayerEnum, time , isHighlighted) ->
            val name = prayerEnum.value
            val icon = prayerEnum.icon
            PrayerItem(
                name = name,
                time = time,
                icon = icon,
                isHighlighted = isHighlighted
            )
        }
    }
}

@Composable
fun PrayerItem(
    name: String,
    time: String,
    icon: Int ,
    isHighlighted: Boolean
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .background(
                if (isHighlighted) Color(0xFFDFF5E3) else Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
            ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
        Text(
            text = time,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}
