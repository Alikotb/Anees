package com.example.anees.ui.screens.radio

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.anees.data.model.radio.RadioStations
import com.example.anees.utils.media_helper.RadioPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RadioViewModel @Inject constructor(private val context: Application) : AndroidViewModel(context) {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val radioStations = RadioStations.stations
    private var currentIndex = 0

    private val _currentStation = MutableStateFlow(radioStations[currentIndex])
    val currentStation = _currentStation.asStateFlow()

    init {
        RadioPlayer.initializePlayer(context)
        playCurrentStation()
    }

    private fun playCurrentStation() {
        RadioPlayer.setMediaItem(_currentStation.value.url)
        _isPlaying.value = false
    }

    fun playPauseRadio() {
        if (RadioPlayer.isPlaying()) {
            RadioPlayer.pause()
            _isPlaying.value = false
        } else {
            RadioPlayer.play()
            _isPlaying.value = true
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
        RadioPlayer.release()
    }
}






