package com.example.todoapp.domain.song

import javax.inject.Inject

class GetSongsUseCase @Inject constructor(
    private val repository: SongRepository
) {

    suspend operator fun invoke(
        searchQuery: String,
        limit: Int = 30
    ): List<Song> {
        return repository.getSongs(
            searchQuery = searchQuery,
            limit = limit
        )
    }
}