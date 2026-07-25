package com.example.todoapp.presention.song

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SongSelectionViewModel @Inject constructor() : ViewModel() {

    private val _selectedSong =
        MutableStateFlow<SelectedSong?>(null)

    val selectedSong = _selectedSong.asStateFlow()

    fun selectSong(
        songName: String,
        songUrl: String
    ) {
        _selectedSong.value = SelectedSong(
            name = songName,
            url = songUrl
        )
    }

    fun clearSelection() {
        _selectedSong.value = null
    }
}

data class SelectedSong(
    val name: String,
    val url: String
)