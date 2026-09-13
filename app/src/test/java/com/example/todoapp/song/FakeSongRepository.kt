package com.example.todoapp.song

import com.example.todoapp.domain.song.Song
import com.example.todoapp.domain.song.SongRepository
import kotlinx.coroutines.delay

class FakeSongRepository : SongRepository {

    var songsToReturn: List<Song> =
        emptyList()

    var getSongsCallCount: Int = 0

    var lastSearchQuery: String? = null

    var shouldThrowError: Boolean = false

    var errorMessage: String =
        "Şarkılar alınamadı"

    var responseDelayMillis: Long = 0L

    override suspend fun getSongs(
        searchQuery: String,
        limit: Int
    ): List<Song> {

        getSongsCallCount++
        lastSearchQuery = searchQuery

        if (responseDelayMillis > 0) {
            delay(responseDelayMillis)
        }

        if (shouldThrowError) {
            throw IllegalStateException(
                errorMessage
            )
        }

        return songsToReturn
    }
}