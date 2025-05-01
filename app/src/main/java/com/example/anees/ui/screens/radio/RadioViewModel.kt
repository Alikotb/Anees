package com.example.anees.ui.screens.radio

import android.app.Application
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.anees.data.model.radio.RadioStations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RadioViewModel @Inject constructor(private val context: Application) : AndroidViewModel(context) {
    private var _player: ExoPlayer? = null
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val radioStations = RadioStations.stations

    private var currentIndex = 0

    private val _currentStation = MutableStateFlow(radioStations[currentIndex])
    val currentStation = _currentStation.asStateFlow()

    init {
        initializePlayer()
    }

    private fun initializePlayer() {
        _player = ExoPlayer.Builder(context).build().apply {
            playWhenReady = false
        }
        playCurrentStation()
    }

    private fun playCurrentStation() {
        _player?.apply {
            stop()
            clearMediaItems()
            val mediaItem = MediaItem.fromUri(_currentStation.value.url)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = false
            _isPlaying.value = false
        }
    }

    fun playPauseRadio() {
        _player?.let { player ->
            if (player.isPlaying) {
                player.pause()
                _isPlaying.value = false
            } else {
                player.play()
                _isPlaying.value = true
            }
        }
    }

    fun nextStation() {
        currentIndex = (currentIndex + 1) % radioStations.size
        _currentStation.value = radioStations[currentIndex]
        playCurrentStation()
    }

    fun previousStation() {
        currentIndex = if (currentIndex - 1 < 0) radioStations.lastIndex else currentIndex - 1
        _currentStation.value = radioStations[currentIndex]
        playCurrentStation()
    }

    override fun onCleared() {
        super.onCleared()
        _player?.release()
    }
}





