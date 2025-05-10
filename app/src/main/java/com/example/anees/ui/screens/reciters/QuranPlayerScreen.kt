package com.example.anees.ui.screens.reciters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anees.enums.RecitersEnum
import com.example.anees.enums.SuraTypeEnum
import com.example.anees.utils.pdf_helper.SuraIndexes
import com.example.anees.R
import com.example.anees.ui.screens.radio.components.ScreenBackground
import com.example.anees.utils.sura_mp3_helper.suraUrls

@Composable
fun QuranPlayerScreen(
    reciter: RecitersEnum = RecitersEnum.Abdelbaset,
    initialSuraIndex: Int = 0,
    onBackClick: () -> Unit = {}
) {
    val viewModel: RecitersViewModel = viewModel()
    val currentSuraIndex by viewModel.currentSuraIndex.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.setCurrentSura(initialSuraIndex, reciter.url)
    }
    val suraName = SuraIndexes[currentSuraIndex].suraName
    val suraTypeIcon = SuraIndexes[currentSuraIndex].type
    val audioUrl = reciter.url + suraUrls[currentSuraIndex].second

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Box(modifier = Modifier.fillMaxSize()  .padding(horizontal = 16.dp)
            .padding(top = 24.dp)) {
            ScreenBackground()
            IconButton(
                onClick = {
                    onBackClick()
                },
                modifier = Modifier.padding( vertical = 24.dp).size(48.dp),
            ){
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Card(
                    shape = CircleShape,
                    modifier = Modifier.size(160.dp),
                ) {
                    Image(
                        painter = painterResource(id = reciter.image),
                        contentDescription = "صورة القارئ",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = reciter.reciter,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = if (suraTypeIcon == SuraTypeEnum.MECCA) R.drawable.kaaba else R.drawable.mosque),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "سُورَةٌ $suraName",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.othmani))
                    )
                }

                Text(
                    text = reciter.description,
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                Mp3Playback(
                    audioUrl = audioUrl,
                    currentSuraIndex = currentSuraIndex,
                    viewModel = viewModel
                )
            }
        }
    }



}


@Composable
fun Mp3Playback(
    audioUrl: String,
    currentSuraIndex: Int,
    viewModel: RecitersViewModel
) {
    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
    val progress by viewModel.progress.collectAsStateWithLifecycle()

    LinearProgressIndicator(
        progress = progress.coerceIn(0f, 1f),
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp)),
        color = Color(0xFF4CAF50)
    )

    Spacer(modifier = Modifier.height(32.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (currentSuraIndex > 0) {
            IconButton(onClick = {
                viewModel.onPrev()
            }) {
                Icon(Icons.Default.SkipPrevious, contentDescription = "السورة السابقة", modifier = Modifier.size(48.dp), tint = Color(0xFF4CAF50))
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp))
        }

        IconButton(onClick = {
            viewModel.playPauseSura()
        }) {
            Icon(
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = "تشغيل / إيقاف",
                modifier = Modifier.size(64.dp),
                tint = Color(0xFF4CAF50)
            )
        }

        if (currentSuraIndex < 113) {
            IconButton(onClick = {
                viewModel.onNext()
            }) {
                Icon(Icons.Default.SkipNext, contentDescription = "السورة التالية", modifier = Modifier.size(48.dp), tint = Color(0xFF4CAF50))
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp))
        }
    }
}
