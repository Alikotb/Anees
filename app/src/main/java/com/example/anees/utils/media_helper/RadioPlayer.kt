package com.example.anees.utils.media_helper

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

object RadioPlayer {

    private var player: ExoPlayer? = null

    fun initializePlayer(context: Context) {
        if (player == null) {
            player = ExoPlayer.Builder(context).build().apply {
                playWhenReady = false
            }
        }
    }

    fun setMediaItem(url: String) {
        player?.apply {
            stop()
            clearMediaItems()
            val mediaItem = MediaItem.fromUri(url)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = false
        }
    }

    fun play() {
        player?.playWhenReady = true
    }

    fun pause() {
        player?.pause()
    }

    fun isPlaying(): Boolean {
        return player?.isPlaying == true
    }

    fun release() {
        player?.release()
        player = null
    }

    fun setListener(listener: Player.Listener) {
        player?.addListener(listener)
    }

    fun removeListener(listener: Player.Listener) {
        player?.removeListener(listener)
    }

    fun getCurrentPosition(): Long {
        return player?.currentPosition ?: 0L
    }

    fun getDuration(): Long {
        return player?.duration ?: 0L
    }

}