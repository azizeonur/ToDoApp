package com.example.todoapp.presention.song

import com.example.todoapp.domain.song.Song

sealed interface SongsUiState {

    data object Loading : SongsUiState

    data class Success(
        val songs: List<Song>
    ) : SongsUiState

    data class Error(
        val message: String
    ) : SongsUiState
}