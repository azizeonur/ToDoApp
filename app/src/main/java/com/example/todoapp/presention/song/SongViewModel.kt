package com.example.todoapp.presention.song

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.song.GetSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SongViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<SongsUiState>(
            SongsUiState.Success(emptyList())
        )

    val uiState = _uiState.asStateFlow()

    private val searchQuery =
        MutableStateFlow("")

    init {
        observeSearchQuery()
    }

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(500L)
                .map { query ->
                    query.trim()
                }
                .distinctUntilChanged()
                .collectLatest { query ->

                    if (query.length < 2) {
                        _uiState.value =
                            SongsUiState.Success(
                                emptyList()
                            )

                        return@collectLatest
                    }

                    searchSongs(query)
                }
        }
    }

    private suspend fun searchSongs(
        query: String
    ) {
        _uiState.value =
            SongsUiState.Loading

        _uiState.value = try {
            val songs = getSongsUseCase(
                searchQuery = query,
                limit = 30
            )

            SongsUiState.Success(songs)
        } catch (exception: Exception) {
            SongsUiState.Error(
                exception.message
                    ?: "Şarkılar yüklenemedi"
            )
        }
    }
}