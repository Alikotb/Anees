package com.example.anees.ui.screens.azkar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.R
import com.example.anees.data.model.ZekrModelItem
import com.example.anees.ui.screens.azkar.component.DetailsCard
import com.example.anees.utils.azkar_helper.AzkarUtils
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
    Box(modifier = Modifier.fillMaxSize()) {
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
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = selectedCategory,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3B3B3B),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                        .padding( bottom = 12.dp)              )
            }
        }

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
    }}
}

