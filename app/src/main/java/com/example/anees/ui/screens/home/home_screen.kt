package com.example.anees.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.ui.screens.home.component.HomeHeader
import com.example.anees.ui.screens.home.component.PrayerCardWithTimer
import com.example.anees.ui.screens.home.component.ComponentCard



@Composable
fun HomeScreen(navToSebiha: () -> Unit = {},
               navToQibla: () -> Unit = {},
               navToQuran: () -> Unit = {},
               navToAzkar: () -> Unit = {},
               navToHadith: () -> Unit = {},
               navToRadio: () -> Unit,
               navToTafsir: () -> Unit,
               navToPrayer: () -> Unit = {} ,
               navToReciters: () -> Unit = {},
               navToNamesOfAllah: () -> Unit = {},
               navToHisnAlMuslim: () -> Unit = {}
){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            val city = "زفتي"
            val country = "مصر"
          
            Spacer(Modifier.height(16.dp))
            ComponentCard()
            PrayerCardWithTimer{
                navToPrayer()
            }
            HomeButton("Azkar") {
                navToAzkar()
            }
            HomeButton("Tasbih") {
                navToSebiha()
            }
            HomeButton("Qibla") {
                navToQibla()
            }
            HomeButton("Quran") {
                navToQuran()
            }
            HomeButton("Hadith") {
                navToHadith()
            }
            HomeButton("Radio") {
                navToRadio()
            }

            HomeButton("Tafsir") {
                navToTafsir()
            }

            HomeButton("Reciters") {
                navToReciters()
            }

            HomeButton("Names") {
                navToNamesOfAllah()
            }
            HomeButton("Hisn Al Muslim") {
                navToHisnAlMuslim()
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun HomeButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp)
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
        elevation = ButtonDefaults.buttonElevation(4.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}






