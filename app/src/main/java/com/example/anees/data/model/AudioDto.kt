package com.example.anees.data.model

data class AudioDto(
    val id: Long,
    val title: String,
    val artist: String?,
    val album: String?,
    val duration: Long,
    val path: String,
    val size: Long,
    val dateAdded: Long,
)

