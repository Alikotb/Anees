package com.example.anees.ui.screens.radio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RadioScreen(viewModel: RadioViewModel = hiltViewModel()) {
    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
    val currentStation by viewModel.currentStation.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Quran Radio", fontSize = 26.sp)
        Spacer(modifier = Modifier.height(24.dp))

        Text(currentStation.name, fontSize = 22.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.previousStation() }) {
                Icon(Icons.Default.SkipPrevious, contentDescription = "Previous", modifier = Modifier.size(64.dp))
            }

            IconButton(onClick = { viewModel.playPauseRadio() }) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Play/Pause",
                    modifier = Modifier.size(72.dp)
                )
            }

            IconButton(onClick = { viewModel.nextStation() }) {
                Icon(Icons.Default.SkipNext, contentDescription = "Next", modifier = Modifier.size(64.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(if (isPlaying) "Now Playing" else "Paused", fontSize = 18.sp)
    }
}

