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
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Icon
import androidx.core.app.NotificationCompat
import androidx.media3.common.Player
import com.example.anees.R
import com.example.anees.data.model.radio.RadioStations
import com.example.anees.utils.media_helper.RadioPlayer
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
        const val CHANNEL_ID = "radio_channel"
        const val NOTIFICATION_ID = 1

        const val ACTION_PLAYBACK_STATE = "com.example.anees.ACTION_PLAYBACK_STATE"
        const val ACTION_STATION_CHANGED = "com.example.anees.ACTION_STATION_CHANGED"
        const val EXTRA_IS_PLAYING = "extra_is_playing"
        const val EXTRA_STATION_INDEX = "extra_station_index"
    }

    private lateinit var audioManager: AudioManager
    private var audioFocusRequest: AudioFocusRequest? = null
    private lateinit var mediaSession: MediaSessionCompat

    private val stations = RadioStations.stations
    private var isSura = false;
    private lateinit var reciterUrl: String
    private var currentIndex = 0

    override fun onCreate() {
        super.onCreate()
        RadioPlayer.initializePlayer(this)

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mediaSession = MediaSessionCompat(this, "AneesRadioSession").apply {
            isActive = true
        }

        createNotificationChannel()

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
                RadioPlayer.play()
                sendPlaybackStateBroadcast(true)
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

        startForeground(NOTIFICATION_ID, buildNotification())
        return START_NOT_STICKY
    }

    private fun startPlayback(url: String) {
        if (requestAudioFocus()) {
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
        val playPauseAction = if (RadioPlayer.isPlaying()) {
            NotificationCompat.Action(
                R.drawable.play_notification, "Pause", notificationIntent(ACTION_PAUSE)
            )
        } else {
            NotificationCompat.Action(
                R.drawable.pause_notification, "Play", notificationIntent(ACTION_PLAY)
            )
        }

        val nextAction = NotificationCompat.Action(
            R.drawable.next_notification, "Next", notificationIntent(ACTION_NEXT)
        )
        val prevAction = NotificationCompat.Action(
            R.drawable.prev_notification, "Previous", notificationIntent(ACTION_PREV)
        )

        val closeAction = NotificationCompat.Action(
            R.drawable.ic_close, "Close", closeIntent()
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(if(isSura) getString(R.string.anees_quran) else getString(R.string.anees_radio))
            .setContentText("Streaming Live")
            .setSmallIcon(R.drawable.logo_foreground)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.zekrback))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
                    .setShowActionsInCompactView(0,1,2)
            )
            .addAction(prevAction)
            .addAction(playPauseAction)
            .addAction(nextAction)
            .addAction(closeAction)
            .setOnlyAlertOnce(true)
            .setOngoing(isAppInForeground())
            .build()
    }

    private fun updateNotification() {
        val notification = buildNotification()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun closeIntent(): PendingIntent {
        val intent = Intent(this, RadioService::class.java).apply {
            action = ACTION_CLOSE
        }
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    private fun notificationIntent(action: String): PendingIntent {
        val intent = Intent(this, RadioService::class.java).apply {
            this.action = action
        }
        return PendingIntent.getService(this, action.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, "Radio Channel", NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
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

    private fun isAppInForeground(): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses = activityManager.runningAppProcesses ?: return false
        val packageName = packageName

        for (processInfo in runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                processInfo.processName == packageName) {
                return true
            }
        }
        return false
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).cancel(NOTIFICATION_ID)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest?.let {
                audioManager.abandonAudioFocusRequest(it)
            }
        } else {
            audioManager.abandonAudioFocus(audioFocusChangeListener)
        }
        RadioPlayer.release()
        mediaSession.release()
        sendPlaybackStateBroadcast(false)

        sendBroadcast(Intent(ACTION_CLOSE))
    }

    private val audioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                RadioPlayer.play()
                sendPlaybackStateBroadcast(true)
            }
            AudioManager.AUDIOFOCUS_LOSS -> {
                RadioPlayer.pause()
                sendPlaybackStateBroadcast(false)
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                RadioPlayer.pause()
                sendPlaybackStateBroadcast(false)
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                RadioPlayer.getPlayer()?.volume = 0.3f
            }
        }
    }

    private fun requestAudioFocus(): Boolean {
        val focusRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setOnAudioFocusChangeListener(audioFocusChangeListener)
                .setAudioAttributes(RadioPlayer.getAudioAttributes())
                .build().also {
                    audioFocusRequest = it
                }
        }else {
            null
        }

        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioManager.requestAudioFocus(focusRequest!!)
        } else {
            audioManager.requestAudioFocus(
                audioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            )
        }

        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
    }
}
