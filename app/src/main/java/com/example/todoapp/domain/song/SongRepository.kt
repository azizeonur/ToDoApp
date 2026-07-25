package com.example.todoapp.domain.song

interface SongRepository {

    suspend fun getSongs(
        searchQuery: String,
        limit: Int = 30
    ): List<Song>
}