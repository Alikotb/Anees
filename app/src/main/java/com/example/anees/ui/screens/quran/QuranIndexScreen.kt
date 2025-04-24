package com.example.anees.ui.screens.quran

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.Enums.SuraTypeEnum
import com.example.anees.R
import com.example.anees.utils.PdfHelper.SuraIndex
import com.example.anees.utils.PdfHelper.SuraIndexes

@Composable
fun QuranIndexScreen() {
    LazyColumn {
        items(SuraIndexes){
            SurahRow(sura = it)
        }
    }

}

@Composable
fun SurahRow(
    sura:SuraIndex
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F5E3)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(
                id = if (sura.type == SuraTypeEnum.MECCA)
                    R.drawable.kaaba else R.drawable.mosque
            ),
            contentDescription = "Surah Icon",
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "آياتها",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "${sura.ayahNumber}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "  سوره ${sura.suraName}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 16.dp)
        )

        Text(
            text = "${sura.index}",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(Color.Black)
                .padding(16.dp),
            color = Color.White
        )
    }
}