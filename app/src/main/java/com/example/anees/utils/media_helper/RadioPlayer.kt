package com.example.anees.utils.media_helper

import android.content.Context
import androidx.media3.common.MediaItem
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
}