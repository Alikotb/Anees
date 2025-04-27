package com.example.anees.ui.screens.hadith

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.anees.utils.hadith_helper.AuthorEdition
import com.example.anees.utils.hadith_helper.cardColors
import com.example.anees.utils.hadith_helper.getSections
import com.example.anees.utils.isInternetAvailable
import com.google.gson.Gson

@Composable
fun HadithSectionsScreen(author: AuthorEdition, navToHadithScreen: (String, String) -> Unit) {
    val ctx = LocalContext.current
    val isOnline = ctx.isInternetAvailable()
    val sectionsMap = getSections(author.apiKey,isOnline)//SectionNamesHelper.sectionsByAuthor[author.apiKey] ?: emptyMap()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(16.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(sectionsMap.entries.toList()) { (number, title) ->
            SectionCard(
                sectionNumber = number,
                sectionTitle = title
            ) { selectedNumber ->
                navToHadithScreen(Gson().toJson(author), selectedNumber)
            }
        }
    }
}

@Composable
fun SectionCard(
    sectionNumber: String,
    sectionTitle: String,
    onClick: (String) -> Unit
) {
    val backgroundColor = remember { cardColors.random() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick(sectionNumber) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = sectionTitle,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}
