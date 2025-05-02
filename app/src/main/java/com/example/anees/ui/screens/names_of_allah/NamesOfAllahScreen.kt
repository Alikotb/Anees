package com.example.anees.ui.screens.names_of_allah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.anees.R
import com.example.anees.ui.screens.radio.components.PlaybackButton
import com.example.anees.ui.screens.radio.components.RadioBackground
import com.example.anees.utils.names_of_allah_helper.getAllNames

@Composable
fun NamesOfAllahScreen() {

    val ctx = LocalContext.current
    val namesList= getAllNames(ctx)

    RadioBackground()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                IconButton(
                    onClick = {  },
                    modifier = Modifier.size(48.dp),
                ){
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                PlaybackButton(size = 36, iconResId = if (true) R.drawable.play else R.drawable.pause) {
                    when {

                    }
                }

                Text(
                    text =  "أسماء الله الحسنى",
                    textAlign = TextAlign.Right,
                    style = MaterialTheme.typography.headlineSmall
                )

            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(namesList) { name ->
                NamesOfAllahCard(name)
            }
        }
    }

}