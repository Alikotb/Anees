package com.example.anees.ui.screens.prayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.anees.R
import com.example.anees.ui.screens.prayer.component.DateSection
import com.example.anees.ui.screens.prayer.component.HeaderWithTimer
import com.example.anees.ui.screens.prayer.component.PrayerList
import com.example.anees.ui.screens.prayer.component.PrayerTopBar
import com.example.anees.utils.date_helper.DateHelper


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

                PrayerTopBar("زفتي , مصر")
                HeaderWithTimer()
                Spacer(modifier = Modifier.height(32.dp))
                DateSection(DateHelper.getTodayHijriDate())
                Spacer(modifier = Modifier.height(8.dp))
                PrayerList()
            }
        }
    }
}

