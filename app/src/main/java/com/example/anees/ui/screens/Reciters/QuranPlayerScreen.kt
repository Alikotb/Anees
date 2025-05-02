package com.example.anees.ui.screens.Reciters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.enums.RecitersEnum
import com.example.anees.enums.SuraTypeEnum
import com.example.anees.utils.pdf_helper.SuraIndexes
import com.example.anees.R
import com.example.anees.ui.screens.radio.components.RadioBackground
import com.example.anees.utils.media_helper.RadioPlayer
import com.example.anees.utils.sura_mp3_helper.suraUrls
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun QuranPlayerScreen(
    reciter: RecitersEnum = RecitersEnum.Abdelbaset,
    initialSuraIndex: Int = 0,
    onBackClick: () -> Unit = {}
) {
    var currentSuraIndex by remember { mutableStateOf(initialSuraIndex) }
    val suraName = SuraIndexes[currentSuraIndex].suraName
    val suraTypeIcon = SuraIndexes[currentSuraIndex].type
    val audioUrl = reciter.url + suraUrls[currentSuraIndex].second

    Box(modifier = Modifier.fillMaxSize()) {
        RadioBackground()
        IconButton(
                onClick = {
                    onBackClick()
                },
                modifier = Modifier.padding(horizontal = 24.dp , vertical = 32.dp).size(48.dp),
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
                onNext = {
                    if (currentSuraIndex < 113) currentSuraIndex++
                },
                onPrevious = {
                    if (currentSuraIndex > 0) currentSuraIndex--
                }
            )
        }
    }


}


@Composable
fun Mp3Playback(
    audioUrl: String,
    currentSuraIndex: Int,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(audioUrl) {
        RadioPlayer.initializePlayer(context)
        RadioPlayer.setMediaItem(audioUrl)
        RadioPlayer.play()
        isPlaying = true
    }

    LaunchedEffect(Unit) {
        while (true) {
            val duration = RadioPlayer.getDuration()
            val position = RadioPlayer.getCurrentPosition()
            if (duration > 0) {
                progress = position / duration.toFloat()
                if (position >= duration - 500 && isPlaying) {
                    if (currentSuraIndex < 113) {
                        onNext()
                    }
                }
            }
            delay(500)
        }
    }


    DisposableEffect(Unit) {
        onDispose {
            RadioPlayer.release()
        }
    }

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
                onPrevious()
            }) {
                Icon(Icons.Default.SkipPrevious, contentDescription = "السورة السابقة", modifier = Modifier.size(48.dp), tint = Color(0xFF4CAF50))
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp))
        }

        IconButton(onClick = {
            if (RadioPlayer.isPlaying()) {
                RadioPlayer.pause()
                isPlaying = false
            } else {
                RadioPlayer.play()
                isPlaying = true
            }
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
                onNext()
            }) {
                Icon(Icons.Default.SkipNext, contentDescription = "السورة التالية", modifier = Modifier.size(48.dp), tint = Color(0xFF4CAF50))
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp))
        }
    }
}
