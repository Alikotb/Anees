package com.example.anees.ui.screens.Reciters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.R
import com.example.anees.enums.RecitersEnum
import com.example.anees.ui.screens.Reciters.component.Mp3Card
import com.example.anees.ui.screens.radio.components.RadioBackground
import com.example.anees.utils.sura_mp3_helper.suraUrls
import com.google.gson.Gson


@Composable
fun SuraMp3Screen(reciter: RecitersEnum ,
                  onBackClick: () -> Unit = {},
                  onSuraClicked : (reciter: String, index: Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        RadioBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Image(
                        painter = painterResource(id = reciter.image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .shadow(2.dp, CircleShape, clip = true)
                    )
                    Text(
                        text = "${reciter.reciter}",
                        textAlign = TextAlign.Right,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(48.dp),
                    ){
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Back")
                    }
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {

                items(suraUrls) { surah ->
                    Mp3Card(
                        surah = surah,
                        index = suraUrls.indexOf(surah) + 1,
                    ){
                        onSuraClicked(Gson().toJson(reciter), it)
                    }
                }
            }
        }}

}