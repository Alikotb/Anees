package com.example.anees.ui.screens.radio

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.anees.data.model.radio.RadioStations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RadioViewModel @Inject constructor(
    private val context: Application
) : AndroidViewModel(context) {

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
        _player = ExoPlayer.Builder(context).build()
        playCurrentStation()
    }

    private fun playCurrentStation() {
        _player?.apply {
            stop()
            clearMediaItems()
            val mediaItem = MediaItem.fromUri(_currentStation.value.url)
            setMediaItem(mediaItem)
            prepare()
            play()
            _isPlaying.value = true
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


