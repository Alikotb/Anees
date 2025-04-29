package com.example.anees.ui.screens.sebha.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.R
import com.example.anees.data.model.Zekir
@Composable
fun ZekirSheetCard(zekir: Zekir, mainZekir: String, onZekirClick: (String) -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    onZekirClick(zekir.arabicName)
                }
            )
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFe9e9d1))
            .border(1.dp, Color.White, RoundedCornerShape(16.dp))
            .padding( vertical = 12.dp)
    ) {
        Text(
            text = zekir.arabicName,
            fontSize = 22.sp,
            color = Color(0xFF2E2E2E),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.othmani)),
            modifier = Modifier.fillMaxWidth().padding( 8.dp)
        )

    }
}