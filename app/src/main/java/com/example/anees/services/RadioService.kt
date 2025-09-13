package com.example.anees.services

import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.media3.common.Player
import com.example.anees.R
import com.example.anees.data.model.radio.RadioStations
import com.example.anees.utils.media_helper.AudioFocusHelper
import com.example.anees.utils.media_helper.RadioNotificationManager
import com.example.anees.utils.media_helper.RadioPlayer
import com.example.anees.utils.pdf_helper.SuraIndexes
import com.example.anees.utils.sura_mp3_helper.suraUrls
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RadioService : Service() {

    companion object {
        const val ACTION_PLAY = "action_play"
        const val ACTION_PAUSE = "action_pause"
        const val ACTION_NEXT = "action_next"
        const val ACTION_PREV = "action_prev"
        const val ACTION_CLOSE = "action_close"
        const val ACTION_PLAYBACK_STATE = "com.example.anees.ACTION_PLAYBACK_STATE"
        const val ACTION_STATION_CHANGED = "com.example.anees.ACTION_STATION_CHANGED"
        const val EXTRA_IS_PLAYING = "extra_is_playing"
        const val EXTRA_STATION_INDEX = "extra_station_index"
    }

    private lateinit var audioManager: AudioManager
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var notificationManager: RadioNotificationManager
    private lateinit var audioFocusHelper: AudioFocusHelper

    private val stations = RadioStations.stations
    private var isSura = false;
    private var reciterName = ""
    private lateinit var reciterUrl: String
    private var currentIndex = 0


    override fun onCreate() {
        super.onCreate()
        RadioPlayer.initializePlayer(this)

        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        mediaSession = MediaSessionCompat(this, "AneesRadioSession").apply {
            isActive = true
        }
        notificationManager = RadioNotificationManager(this, mediaSession)
        audioFocusHelper = AudioFocusHelper(
            context = this,
            onFocusGain = {
                RadioPlayer.play()
                sendPlaybackStateBroadcast(true)
            },
            onFocusLoss = {
                RadioPlayer.pause()
                sendPlaybackStateBroadcast(false)
            } ,
            onDuck = {
                RadioPlayer.getPlayer()?.volume = 0.3f
            } ,
        )

        RadioPlayer.setListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                updateNotification()
            }
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_CLOSE -> {
                stopSelf()
                return START_NOT_STICKY
            }
            ACTION_PLAY -> {
                if (audioFocusHelper.requestAudioFocus()) {
                    RadioPlayer.play()
                    sendPlaybackStateBroadcast(true)
                }
            }
            ACTION_PAUSE -> {
                RadioPlayer.pause()
                sendPlaybackStateBroadcast(false);
            }
            ACTION_NEXT -> {
                playNext()
                sendStationChangedBroadcast(currentIndex)
            }
            ACTION_PREV -> {
                playPrev()
                sendStationChangedBroadcast(currentIndex)
            }
            else -> {
                val url = intent?.getStringExtra("url")
                val index = intent?.getIntExtra("index", 0) ?: 0
                val isRadio = intent?.getBooleanExtra("isRadio", true) ?: true
                val reciter = intent?.getStringExtra("reciterUrl") ?: ""
                reciterName = intent?.getStringExtra("reciterName") ?: ""
                url?.let {
                    startPlayback(it)
                    sendPlaybackStateBroadcast(true)
                    currentIndex = if (isRadio) {
                        stations.indexOfFirst { station -> station.url == url }
                    }else {
                        reciterUrl = reciter
                        isSura = true
                        index
                    }
                    sendStationChangedBroadcast(currentIndex)
                }
            }
        }

        startForeground(RadioNotificationManager.NOTIFICATION_ID, buildNotification())
        return START_NOT_STICKY
    }

    private fun startPlayback(url: String) {
        if (audioFocusHelper.requestAudioFocus()) {
            RadioPlayer.setMediaItem(url)
            RadioPlayer.play()
        }
    }

    private fun playNext() {
        if(isSura) {
            if (currentIndex < 113) currentIndex++
            startPlayback(reciterUrl + suraUrls[currentIndex].second)
        }else {
            currentIndex = (currentIndex + 1) % stations.size
            startPlayback(stations[currentIndex].url)
        }
    }

    private fun playPrev() {
        if(isSura) {
            if (currentIndex > 0) currentIndex--
            startPlayback(reciterUrl + suraUrls[currentIndex].second)
        }else {
            currentIndex = if (currentIndex - 1 < 0) stations.lastIndex else currentIndex - 1
            startPlayback(stations[currentIndex].url)
        }
    }

    private fun buildNotification(): Notification {
        if (isSura) {
            return notificationManager.buildNotification(reciterName, SuraIndexes[currentIndex].suraName)
        }
        return notificationManager.buildNotification(
            getString(R.string.anees_radio),
            stations[currentIndex].name
        )
    }

    private fun updateNotification() {
        val notification = buildNotification()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(RadioNotificationManager.NOTIFICATION_ID, notification)
    }

    private fun sendPlaybackStateBroadcast(isPlaying: Boolean) {
        val intent = Intent(ACTION_PLAYBACK_STATE).apply {
            putExtra(EXTRA_IS_PLAYING, isPlaying)
        }
        sendBroadcast(intent)
    }

    private fun sendStationChangedBroadcast(index: Int) {
        val intent = Intent(ACTION_STATION_CHANGED).apply {
            putExtra(EXTRA_STATION_INDEX, index)
        }
        sendBroadcast(intent)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        RadioPlayer.release()
        audioFocusHelper.abandonAudioFocus()
        mediaSession.release()
        sendPlaybackStateBroadcast(false)
        sendBroadcast(Intent(ACTION_CLOSE))
    }

}
