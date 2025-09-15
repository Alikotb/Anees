package com.example.anees.utils.downloaded_audio

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.core.graphics.scale
fun getAlbumArt(context: Context, path: String?, size: Int = 256): Bitmap? {
    if (path.isNullOrEmpty()) return null

    return try {
        val mmr = MediaMetadataRetriever()

        if (path.startsWith("content://") || path.startsWith("file://")) {
            mmr.setDataSource(context, Uri.parse(path))
        } else {
            mmr.setDataSource(path) // direct file path
        }

        val art = mmr.embeddedPicture
        if (art != null) {
            val original = BitmapFactory.decodeByteArray(art, 0, art.size)
            original.scale(size, size)
        } else null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

