package com.example.anees.ui.screens.tafsir.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.anees.enums.QuranSurah
import com.example.anees.ui.screens.tafsir.component.TafsirDeatilsCard
import androidx.compose.runtime.getValue
import com.example.anees.data.model.Response
import com.example.anees.data.model.TafsierModel
import com.example.anees.ui.screens.tafsir.TafsirViewModel

@Composable
fun TafsirDetailsScreen(surah: QuranSurah) {

    val viewModel: TafsirViewModel = hiltViewModel()
    val tafsir by viewModel.tafsirSurah.collectAsStateWithLifecycle()

    LaunchedEffect(surah.number) {
        viewModel.getTafsirSurah(surah.number)
    }

    when (val state = tafsir) {
        is Response.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            ) {
                CircularProgressIndicator()
            }
        }

        is Response.Success -> {
            Screen(surah, state.data)
        }

        is Response.Error -> {
            Text(
                text = state.message,
                color = Color.Red,
                fontSize = 16.sp,
            )
        }

    }


}

@Composable
fun Screen(surah: QuranSurah, tafsir: TafsierModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "سُورَةٌ ${surah.arabicName}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3B3B3B),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(tafsir.result) {
                TafsirDeatilsCard(it)
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
