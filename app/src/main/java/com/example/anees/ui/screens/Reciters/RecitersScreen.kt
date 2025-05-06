package com.example.anees.ui.screens.Reciters

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.R
import com.example.anees.enums.RecitersEnum
import com.example.anees.ui.screens.hadith.components.ScreenTitle
import com.example.anees.ui.screens.radio.components.ScreenBackground

@Preview(showBackground = true)
@Composable
fun RecitersScreen(onBackClick: () -> Unit = {}, navToSuraMp3: (RecitersEnum) -> Unit = {}) {
    Box {
        ScreenBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 24.dp)
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                ScreenTitle( title = "قراء القرأن الكريم", onBackClick = {onBackClick() }, size = 24)
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 48.dp)
            ) {
                items(RecitersEnum.entries.toTypedArray()) { reciter ->
                    ReciterCard(reciter = reciter){
                        navToSuraMp3(it)
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ReciterCard(
    reciter: RecitersEnum = RecitersEnum.Abdelbaset,
    navToSuraMp3: (RecitersEnum) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                navToSuraMp3(reciter)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box{
            Image(
                painter = painterResource(id = R.drawable.authorbg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = reciter.image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                        .shadow(8.dp, CircleShape, clip = true)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = reciter.reciter,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
