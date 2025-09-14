package com.example.anees.utils.downloaded_audio

import android.content.Context
import android.os.Build
import android.provider.MediaStore
import com.example.anees.data.model.audio.AudioDto


 fun loadAllAudio( context: Context): List<AudioDto> {
    val musicList = mutableListOf<AudioDto>()
    val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
    } else {
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    }

    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.DISPLAY_NAME,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.DATA,
        MediaStore.Audio.Media.SIZE,
        MediaStore.Audio.Media.DATE_ADDED,
        MediaStore.Audio.Media.ALBUM_ID
    )

     val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0 AND ${MediaStore.Audio.Media.RELATIVE_PATH} LIKE ?"
     val selectionArgs = arrayOf("%Music/Anees%")


     val cursor = context.contentResolver.query(
        collection,
        projection,
        selection,
         selectionArgs,
        "${MediaStore.Audio.Media.DATE_ADDED} DESC"
    )

    cursor?.use {
        val idCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        val titleCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
        val artistCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
        val albumCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
        val durationCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
        val pathCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        val sizeCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
        val dateCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)

        while (it.moveToNext()) {
            val path = it.getString(pathCol)
            val music = AudioDto(
                id = it.getLong(idCol),
                title = it.getString(titleCol),
                artist = it.getString(artistCol),
                album = it.getString(albumCol),
                duration = it.getLong(durationCol),
                path = path,
                size = it.getLong(sizeCol),
                dateAdded = it.getLong(dateCol),
            )
            musicList.add(music)
        }
    }
    return musicList
}