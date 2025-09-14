package com.example.anees.ui.screens.reciters.view.screens

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anees.R
import com.example.anees.enums.RecitersEnum
import com.example.anees.enums.SuraTypeEnum
import com.example.anees.services.RadioService
import com.example.anees.ui.screens.radio.components.ScreenBackground
import com.example.anees.ui.screens.reciters.view_model.RecitersViewModel
import com.example.anees.utils.SharedModel

@Composable
fun QuranPlayerScreen(
    reciter: RecitersEnum? = RecitersEnum.Abdelbaset,
    initialSuraIndex: Int = 0,
    isOnline: Boolean = true,
    onBackClick: () -> Unit = {}
) {
    val viewModel: RecitersViewModel = viewModel()
    val currentTrack by viewModel.currentTrack.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (isOnline) {
            viewModel.setOnlinePlaylist(reciter!!)
            viewModel.playSura(initialSuraIndex)
        } else {
            viewModel.setOfflinePlaylist()
            viewModel.playSura(initialSuraIndex)
        }
    }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (SharedModel.isAppActive) {
                    onBackClick()
                } else {
                    (context as Activity).finishAffinity()
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(
                receiver,
                IntentFilter(RadioService.ACTION_CLOSE),
                Context.RECEIVER_NOT_EXPORTED
            )
        } else {
            ContextCompat.registerReceiver(
                context,
                receiver,
                IntentFilter(RadioService.ACTION_CLOSE),
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
        }

        onDispose {
            context.unregisterReceiver(receiver)
            val stopIntent = Intent(context, RadioService::class.java)
            context.stopService(stopIntent)
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        ) {
            ScreenBackground()
            IconButton(
                onClick = {
                    onBackClick()
                },
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .size(48.dp),
            ) {
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
                        painter = painterResource(
                            id = currentTrack?.reciterImage ?: R.drawable.sound
                        ),
                        contentDescription = "صورة القارئ",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = currentTrack?.reciter ?: "",
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
                        painter = painterResource(id = if (currentTrack?.typeIcon == SuraTypeEnum.MECCA.name) R.drawable.kaaba else R.drawable.mosque),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "سُورَةٌ ${currentTrack?.title}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.othmani))
                    )
                }

                Text(
                    text = currentTrack?.description ?: "",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                Mp3Playback(
                    viewModel = viewModel,
                    isOnline = isOnline
                )
            }
        }
    }
}


@Composable
fun Mp3Playback(
    viewModel: RecitersViewModel,
    isOnline: Boolean
) {
    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
    val progress by viewModel.progress.collectAsStateWithLifecycle()
    val currentSuraIndex by viewModel.currentSuraIndex.collectAsStateWithLifecycle()
    val playlist by viewModel.playList.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.getNextSura()
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
                viewModel.onPrev()
            }) {
                Icon(
                    Icons.Default.SkipPrevious,
                    contentDescription = "السورة السابقة",
                    modifier = Modifier.size(48.dp),
                    tint = Color(0xFF4CAF50)
                )
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

        if (currentSuraIndex < playlist.lastIndex) {
            IconButton(onClick = {
                viewModel.onNext()
            }) {
                Icon(
                    Icons.Default.SkipNext,
                    contentDescription = "السورة التالية",
                    modifier = Modifier.size(48.dp),
                    tint = Color(0xFF4CAF50)
                )
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp))
        }
        if (isOnline) {
            IconButton(
                onClick = {
                    viewModel.downloadCurrentSura()
                }
            ) {
                Icon(
                    Icons.Default.Download,
                    contentDescription = "تحميل",
                    modifier = Modifier.size(32.dp),
                    tint = Color(0xFF4CAF50)
                )
            }
        }
    }
}
