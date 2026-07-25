package com.example.todoapp.domain.song

import com.example.todoapp.data.remote.dto.SongDto

data class Song(
    val id: String,
    val name: String,
    val duration: Int,
    val artistName: String,
    val imageUrl: String,
    val audioUrl: String
)

fun SongDto.toDomain(): Song {
    return Song(
        id = id,
        name = name,
        artistName = artistName,
        duration = duration,
        imageUrl = image,
        audioUrl = audio,
    )
}