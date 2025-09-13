package com.example.anees.ui.screens.reciters.view_model

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.anees.enums.RecitersEnum
import com.example.anees.receivers.RadioBroadcastReceiver
import com.example.anees.services.RadioService
import com.example.anees.utils.media_helper.RadioPlayer
import com.example.anees.utils.media_helper.RadioServiceManager
import com.example.anees.utils.pdf_helper.SuraIndexes
import com.example.anees.utils.sura_mp3_helper.suraUrls
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecitersViewModel @Inject constructor(private val context: Application) :
    AndroidViewModel(context) {

    private val _currentSuraIndex = MutableStateFlow(0)
    val currentSuraIndex = _currentSuraIndex.asStateFlow()

    private val _reciterUrl = MutableStateFlow(RecitersEnum.Abdelbaset)
    val reciterUrl = _reciterUrl.asStateFlow()

    private val _currentSura = MutableStateFlow(SuraIndexes[0])
    val currentSura = _currentSura.asStateFlow()

    private val _currentSuraTypeIcon = MutableStateFlow(SuraIndexes[0].type)
    val currentSuraTypeIcon = _currentSuraTypeIcon.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress = _progress.asStateFlow()

    private var isProgressActive = true

    private var broadcastReceiver: RadioBroadcastReceiver? = null

    init {
        setupBroadcastReceiver()
    }

    fun setCurrentSura(
        index: Int = 0,
        reciterUrl: RecitersEnum = RecitersEnum.Abdelbaset,
    ) {

            _currentSuraIndex.value = index
            _currentSura.value = SuraIndexes[index]
            _currentSuraTypeIcon.value = SuraIndexes[index].type
            _reciterUrl.value = reciterUrl

            RadioServiceManager.startRadioService(
                context,
                reciterUrl.url + suraUrls[index].second,
                _reciterUrl.value.url,
                _currentSuraIndex.value,
                _reciterUrl.value.reciter,
                false
            )


    }

    fun playPauseSura() {
        if (RadioPlayer.isPlaying()) {
            RadioServiceManager.sendRadioAction(context, RadioService.Companion.ACTION_PAUSE)
            _isPlaying.value = false
        } else {
            RadioServiceManager.sendRadioAction(context, RadioService.Companion.ACTION_PLAY)
            _isPlaying.value = true
        }
    }

    fun getNextSura() {
        viewModelScope.launch {
            isProgressActive = true
            while (isProgressActive) {
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
        RadioServiceManager.startRadioService(
            context,
            reciterUrl.value.url + suraUrls[_currentSuraIndex.value].second,
            reciterUrl.value.url,
            _currentSuraIndex.value,
            reciterUrl.value.reciter,
            false
        )
        _isPlaying.value = true
    }

    fun onNext() {
            if (_currentSuraIndex.value < 113) {
                _currentSuraIndex.value++
                _currentSura.value = SuraIndexes[_currentSuraIndex.value]
                _currentSuraTypeIcon.value = SuraIndexes[_currentSuraIndex.value].type
            }
            startRadioService()

    }

    fun onPrev() {
        if (_currentSuraIndex.value > 0) {
            _currentSuraIndex.value--
            _currentSura.value = SuraIndexes[_currentSuraIndex.value]
            _currentSuraTypeIcon.value = SuraIndexes[_currentSuraIndex.value].type
        }
        startRadioService()
    }

    private fun setupBroadcastReceiver() {
        broadcastReceiver = RadioBroadcastReceiver(
            onPlayPause = { isPlaying ->
                _isPlaying.value = isPlaying
            },
            onStationChanged = { index ->
                if (index != _currentSuraIndex.value) {
                    _currentSuraIndex.value = index
                    _currentSuraIndex.value = index
                    _currentSura.value = SuraIndexes[index]
                    _currentSuraTypeIcon.value = SuraIndexes[index].type
                }
            }
        )

        val filter = IntentFilter().apply {
            addAction(RadioBroadcastReceiver.Companion.ACTION_PLAYBACK_STATE)
            addAction(RadioBroadcastReceiver.Companion.ACTION_STATION_CHANGED)
            priority = IntentFilter.SYSTEM_HIGH_PRIORITY
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(broadcastReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            ContextCompat.registerReceiver(
                context,
                broadcastReceiver,
                filter,
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
        }
    }

    fun downloadCurrentSura() {
        val index = _currentSuraIndex.value
        val suraName = SuraIndexes[index].suraName
        val suraUrl = _reciterUrl.value.url + suraUrls[index].second
        val fileName =
            "$suraName - ${_reciterUrl.value.reciter} - ${_reciterUrl.value.description}.mp3"
        val request = DownloadManager.Request(suraUrl.toUri())
            .setTitle("Downloading ${suraName + " " + reciterUrl.value.reciter}")
            .setDescription("جارٍ تحميل السورة${suraName}")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_MUSIC,
            "Anees/$fileName"
        )

        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }


    override fun onCleared() {
        super.onCleared()
        isProgressActive = false
        RadioPlayer.release()
        broadcastReceiver?.let {
            context.unregisterReceiver(it)
        }
    }
}