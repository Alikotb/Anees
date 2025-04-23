package com.example.anees.ui.screens.azkar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.data.model.ZekrModelItem
import com.example.anees.ui.screens.azkar.component.DetailsCard
import com.example.anees.utils.AzkarUtils
import kotlinx.coroutines.delay

@Composable
fun AdhkarDetailsScreen(selectedCategory: String,navToAzkar:  () -> Unit = {}) {
    val context = LocalContext.current
    val azkarList = remember { AzkarUtils.parseAdhkar(context) }
    val originalList: List<ZekrModelItem> = remember { AzkarUtils.getAzkarByCategory(azkarList, selectedCategory) }

    val azkar = remember {
        mutableStateListOf<ZekrModelItem>().apply {
            addAll(originalList)
        }
    }
    LaunchedEffect(azkar.size) {
        if (azkar.isEmpty()) {
            delay(300)
            navToAzkar()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .padding(16.dp)
    ) {
        Text(
            text = "الأذكار اليومية",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3B3B3B),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
                .padding(top = 56.dp, bottom = 24.dp, end = 12.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(azkar) { asd ->
                DetailsCard(
                    zekr = asd,
                    onCountDecrement = {
                        val index = azkar.indexOf(asd)
                        if (index != -1) {
                            val newCount = azkar[index].count - 1
                            if (newCount <= 0) {
                                azkar.removeAt(index)
                            } else {
                                azkar[index] = asd.copy(count = newCount)
                            }
                        }
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

