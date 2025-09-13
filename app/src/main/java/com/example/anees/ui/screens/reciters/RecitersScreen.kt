package com.example.anees.ui.screens.reciters

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.anees.enums.Recitations
import com.example.anees.enums.RecitersEnum
import com.example.anees.ui.screens.hadith.components.ScreenTitle
import com.example.anees.ui.screens.radio.components.ScreenBackground
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import com.example.anees.data.model.RecitationModel

@Preview(showBackground = true)
@Composable
fun RecitersScreen(
    onBackClick: () -> Unit = {},
    navToSuraMp3: (RecitersEnum) -> Unit = {}
) {
    var selectedRecitation by remember { mutableStateOf(Recitations.HAFS_AN_ASIM) }

    Box {
        ScreenBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 24.dp)
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                ScreenTitle(
                    title = "قراء القرأن الكريم",
                    onBackClick = { onBackClick() },
                    size = 24
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            RecitationsDropdownMenu { recitation ->
                selectedRecitation = recitation
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 48.dp)
            ) {
                items(selectedRecitation.list) { reciter ->
                    ReciterCard(recitation = reciter) {
                        navToSuraMp3(it)
                    }
                }
            }
        }
    }
}

@Composable
fun ReciterCard(
    recitation: RecitationModel,
    navToSuraMp3: (RecitersEnum) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                navToSuraMp3(RecitersEnum.Abdelbaset)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
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
                AsyncImage(
                    model = recitation.reciter.imageUrl,
                    contentDescription = recitation.reciter.reciterName,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                        .shadow(8.dp, CircleShape, clip = true),
                    contentScale = ContentScale.FillBounds

                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = recitation.reciter.reciterName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Composable
fun RecitationsDropdownMenu(
    onRecitationSelected: (Recitations) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedRecitation by remember { mutableStateOf(Recitations.HAFS_AN_ASIM) }

    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(
                containerColor =   Color(0xFF5A2E0E),

                contentColor = Color.White
            )
        ) {
            Text(text = selectedRecitation.recitationName)


    }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Recitations.values().forEach { recitation ->
                DropdownMenuItem(
                    text = { Text(recitation.recitationName) },
                    onClick = {
                        selectedRecitation = recitation
                        expanded = false
                        onRecitationSelected(recitation)
                    }
                )
            }
        }
    }
}

