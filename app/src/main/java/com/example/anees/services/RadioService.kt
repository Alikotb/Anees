package com.example.anees.services

import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.media3.common.Player
import com.example.anees.R
import com.example.anees.data.model.radio.RadioStations
import com.example.anees.utils.media_helper.RadioPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RadioService : Service() {

    companion object {
        const val ACTION_PLAY = "action_play"
        const val ACTION_PAUSE = "action_pause"
        const val ACTION_NEXT = "action_next"
        const val ACTION_PREV = "action_prev"
        const val CHANNEL_ID = "radio_channel"
        const val NOTIFICATION_ID = 1

        const val ACTION_PLAYBACK_STATE = "com.example.anees.ACTION_PLAYBACK_STATE"
        const val ACTION_STATION_CHANGED = "com.example.anees.ACTION_STATION_CHANGED"
        const val ACTION_REQUEST_STATE = "action_request_state"
        const val EXTRA_IS_PLAYING = "extra_is_playing"
        const val EXTRA_STATION_INDEX = "extra_station_index"
    }

    private lateinit var mediaSession: MediaSessionCompat
    private val stations = RadioStations.stations
    private var currentIndex = 0

    override fun onCreate() {
        super.onCreate()
        RadioPlayer.initializePlayer(this)
        mediaSession = MediaSessionCompat(this, "AneesRadioSession").apply {
            isActive = true
        }

        mediaSession.setCallback(object : MediaSessionCompat.Callback() {
            override fun onPlay() {
                RadioPlayer.play()
                sendPlaybackStateBroadcast(true)
            }

            override fun onPause() {
                RadioPlayer.pause()
                sendPlaybackStateBroadcast(false)
            }

            override fun onSkipToNext() {
                playNext()
                sendStationChangedBroadcast(currentIndex)
            }

            override fun onSkipToPrevious() {
                playPrev()
                sendStationChangedBroadcast(currentIndex)
            }
        })

        createNotificationChannel()

        RadioPlayer.setListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                updateNotification()
            }
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_REQUEST_STATE -> {
                sendPlaybackStateBroadcast(RadioPlayer.isPlaying())
                sendStationChangedBroadcast(currentIndex)
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
                url?.let {
                    startPlayback(it)
                    sendPlaybackStateBroadcast(true)
                    currentIndex = stations.indexOfFirst { station -> station.url == url }
                    sendStationChangedBroadcast(currentIndex)
                }
            }
        }

        startForeground(NOTIFICATION_ID, buildNotification())
        return START_STICKY
    }

    private fun startPlayback(url: String) {
        RadioPlayer.setMediaItem(url)
        RadioPlayer.play()
    }

    private fun playNext() {
        currentIndex = (currentIndex + 1) % stations.size
        startPlayback(stations[currentIndex].url)
    }

    private fun playPrev() {
        currentIndex = if (currentIndex - 1 < 0) stations.lastIndex else currentIndex - 1
        startPlayback(stations[currentIndex].url)
    }

    private fun buildNotification(): Notification {
        val playPauseAction = if (RadioPlayer.isPlaying()) {
            NotificationCompat.Action(
                R.drawable.pause, "Pause", notificationIntent(ACTION_PAUSE)
            )
        } else {
            NotificationCompat.Action(
                R.drawable.playnot, "Play", notificationIntent(ACTION_PLAY)
            )
        }

        val nextAction = NotificationCompat.Action(
            R.drawable.right, "Next", notificationIntent(ACTION_NEXT)
        )
        val prevAction = NotificationCompat.Action(
            R.drawable.back, "Previous", notificationIntent(ACTION_PREV)
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Anees Radio")
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
            .setOnlyAlertOnce(true)
            .setOngoing(isAppInForeground())
            .setDeleteIntent(deleteIntent())
            .build()
    }

    private fun updateNotification() {
        val notification = buildNotification()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun notificationIntent(action: String): PendingIntent {
        val intent = Intent(this, RadioService::class.java).apply {
            this.action = action
        }
        return PendingIntent.getService(this, action.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    private fun deleteIntent(): PendingIntent {
        val intent = Intent(this, RadioService::class.java).apply {
            action = "ACTION_DELETE_NOTIFICATION"
        }
        return PendingIntent.getService(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
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

    override fun onDestroy() {
        super.onDestroy()
        RadioPlayer.release()
        mediaSession.release()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        RadioPlayer.release()
        mediaSession.release()
        stopSelf()
    }
}
