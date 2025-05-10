package com.example.anees.ui.screens.reciters

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.anees.enums.RecitersEnum
import com.example.anees.receivers.RadioBroadcastReceiver
import com.example.anees.services.RadioService
import com.example.anees.utils.media_helper.RadioPlayer
import com.example.anees.utils.media_helper.RadioServiceManager
import com.example.anees.utils.sura_mp3_helper.suraUrls
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecitersViewModel @Inject constructor(private val context: Application) : AndroidViewModel(context) {

    private val _currentSuraIndex = MutableStateFlow(0)
    val currentSuraIndex = _currentSuraIndex.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress = _progress.asStateFlow()

    private val reciter = RecitersEnum.Abdelbaset

    private var broadcastReceiver: RadioBroadcastReceiver? = null

    init {
        setupBroadcastReceiver()
        //RadioServiceManager.startRadioService(context, reciter.url + suraUrls[_currentSuraIndex].second)
        RadioPlayer.initializePlayer(context)
    }

    fun setCurrentSura(index: Int, reciterUrl: String) {
        _currentSuraIndex.value = index
        RadioServiceManager.startRadioService(context, reciterUrl + suraUrls[index].second)
    }

    fun playPauseSura() {
        if (RadioPlayer.isPlaying()) {
            RadioServiceManager.sendRadioAction(context, RadioService.ACTION_PAUSE)
            _isPlaying.value = false
        } else {
            RadioServiceManager.sendRadioAction(context, RadioService.ACTION_PLAY)
            _isPlaying.value = true
        }
    }

    fun getNextSura() {
        viewModelScope.launch {
            while (true) {
                val duration = RadioPlayer.getDuration()
                val position = RadioPlayer.getCurrentPosition()
                if (duration > 0) {
                    _progress.value = position / duration.toFloat()
                    if (position >= duration - 500 && _isPlaying.value) {
                        if (_currentSuraIndex.value < 113) {
                            onNext()
                        }
                    }
                }
                delay(500)
            }
        }
    }

    private fun startRadioService() {
        RadioServiceManager.startRadioService(context, reciter.url + suraUrls[_currentSuraIndex.value].second)
        _isPlaying.value = true
    }

    fun onNext() {
        if (_currentSuraIndex.value < 113) _currentSuraIndex.value++
    }

    fun onPrev() {
        if (_currentSuraIndex.value > 0) _currentSuraIndex.value--
    }

    private fun setupBroadcastReceiver() {
        broadcastReceiver = RadioBroadcastReceiver(
            onPlayPause = { isPlaying ->
                _isPlaying.value = isPlaying
            },
            onStationChanged = { index ->
                _currentSuraIndex.value = index
            }
        )

        val filter = IntentFilter().apply {
            addAction(RadioBroadcastReceiver.ACTION_PLAYBACK_STATE)
            addAction(RadioBroadcastReceiver.ACTION_STATION_CHANGED)
            priority = IntentFilter.SYSTEM_HIGH_PRIORITY
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(broadcastReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            ContextCompat.registerReceiver(context, broadcastReceiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED)
        }
    }

    override fun onCleared() {
        super.onCleared()
        RadioPlayer.release()
        broadcastReceiver?.let {
            context.unregisterReceiver(it)
        }
    }
}