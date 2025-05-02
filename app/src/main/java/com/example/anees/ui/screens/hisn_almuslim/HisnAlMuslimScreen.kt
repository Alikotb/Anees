package com.example.anees.ui.screens.hisn_almuslim

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.R
import com.example.anees.ui.screens.hadith.components.ScreenTitle
import com.example.anees.ui.screens.radio.components.ScreenBackground
import com.example.anees.utils.hisn_almuslim_helper.HisnAlMuslimHelper

@Composable
fun HisnAlMuslimScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val fileName = "hisn/hisn_almuslim.json"

    val titlesWithTexts = remember {
        HisnAlMuslimHelper.getTitles(context, fileName).map { title ->
            val (texts, _) = HisnAlMuslimHelper.getSectionContent(context, fileName, title)
            title to texts
        }
    }

    ScreenBackground()
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
    ){
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            ScreenTitle(
                title = "حصن المسلم",
                onBackClick = onBackClick
            )
        }
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                titlesWithTexts.forEach { (title, texts) ->
                    item {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontFamily = FontFamily(Font(R.font.othmani)),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
                        )
                    }

                    items(texts.size) { index ->
                        HisnAlMuslimTextCard(textContent = texts[index])
                    }

                }
                item {
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }
    }
}

@Composable
fun HisnAlMuslimTextCard(textContent: String) {
    val highlightedText = buildAnnotatedString {
        val words = textContent.split(" ")
        for (word in words) {
            if (word.contains("الله")) {
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("$word ")
                }
            } else {
                withStyle(style = SpanStyle(color = Color(0xFF6F3A18))) {
                    append("$word ")
                }
            }
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, Color(0xEB803F0B), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Text(
                text = highlightedText,
                fontSize = 16.sp,
                color = Color(0xFF281102),
                fontFamily = FontFamily(Font(R.font.othmani)),
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


