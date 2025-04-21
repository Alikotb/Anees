package com.example.anees.ui.screens.sebha.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.data.model.Zekir

@Composable
fun ZekirCard(zero: Zekir, onClick: () -> Unit, index: Int, currentIndex: Int) {
    Card(
        colors = CardDefaults.cardColors(
            if (index == currentIndex) Color(0xFF8D6E63) else Color(0xFFE0E0E0),
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .clickable {
               onClick()
            }
    ) {
        Text(
            text = zero.arabicName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (index == currentIndex) Color.White else Color.Black,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        )
    }
}
