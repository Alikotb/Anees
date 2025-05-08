package com.example.anees.ui.screens.radio

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.anees.data.model.radio.RadioStations
import com.example.anees.services.RadioService
import com.example.anees.utils.media_helper.RadioPlayer
import com.example.anees.utils.media_helper.RadioServiceManager
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

    fun playPauseRadio() {
        if (_isPlaying.value) {
            RadioServiceManager.sendRadioAction(context, RadioService.ACTION_PAUSE)
            _isPlaying.value = false
        } else {
            RadioServiceManager.sendRadioAction(context, RadioService.ACTION_PLAY)
            _isPlaying.value = true
        }
    }

    fun nextStation() {
        currentIndex = (currentIndex + 1) % radioStations.size
        _currentStation.value = radioStations[currentIndex]
        startRadioService()
    }

    fun previousStation() {
        currentIndex = if (currentIndex - 1 < 0) radioStations.lastIndex else currentIndex - 1
        _currentStation.value = radioStations[currentIndex]
        startRadioService()
    }

    private fun startRadioService() {
        RadioServiceManager.startRadioService(context, _currentStation.value.url)
        _isPlaying.value = true
    }

    fun stopRadio() {
        RadioServiceManager.stopRadioService(context)
        _isPlaying.value = false
    }
}






