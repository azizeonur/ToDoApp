package com.example.todoapp.presention.song

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.todoapp.domain.song.Song
import com.example.todoapp.receiver.AlarmSoundPlayer


@Composable
fun SongScreen(
    onBack: () -> Unit,
    selectionViewModel: SongSelectionViewModel,
    viewModel: SongViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var searchQuery by remember {
        mutableStateOf("")
    }

    var previewSongId by remember {
        mutableStateOf<String?>(null)
    }

    DisposableEffect(Unit) {
        onDispose {
            AlarmSoundPlayer.stop()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                viewModel.onSearchQueryChange(query)
            },
            placeholder = {
                Text("Şarkı veya sanatçı ara")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Ara"
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        when (val state = uiState) {

            SongsUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    )
                )
            }

            is SongsUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error
                )
            }

            is SongsUiState.Success -> {

                when {
                    searchQuery.trim().length < 2 -> {
                        Text(
                            text = "Aramak için en az 2 harf yaz",
                            modifier = Modifier.align(
                                Alignment.CenterHorizontally
                            )
                        )
                    }

                    state.songs.isEmpty() -> {
                        Text(
                            text = "Şarkı bulunamadı",
                            modifier = Modifier.align(
                                Alignment.CenterHorizontally
                            )
                        )
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                items = state.songs,
                                key = { song ->
                                    song.id
                                }
                            ) { song ->

                                SongItem(
                                    songName = song.name,
                                    artistName = song.artistName,
                                    imageUrl = song.imageUrl,
                                    isPreviewPlaying =
                                        previewSongId == song.id,
                                    onPreviewClick = {
                                        handlePreviewClick(
                                            song = song,
                                            currentPreviewSongId =
                                                previewSongId,
                                            onPreviewSongIdChange = {
                                                previewSongId = it
                                            }
                                        )
                                    },
                                    onClick = {
                                        AlarmSoundPlayer.stop()
                                        previewSongId = null

                                        selectionViewModel.selectSong(
                                            songName = song.name,
                                            songUrl = song.audioUrl
                                        )

                                        onBack()
                                    }
                                )

                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun handlePreviewClick(
    song: Song,
    currentPreviewSongId: String?,
    onPreviewSongIdChange: (String?) -> Unit
) {
    if (currentPreviewSongId == song.id) {
        AlarmSoundPlayer.stop()
        onPreviewSongIdChange(null)
    } else {
        AlarmSoundPlayer.play(
            songUrl = song.audioUrl,
            durationMillis = 15_000L
        )

        onPreviewSongIdChange(song.id)
    }
}

@Composable
fun SongItem(
    songName: String,
    artistName: String,
    imageUrl: String,
    isPreviewPlaying: Boolean,
    onPreviewClick: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        AsyncImage(
            model = imageUrl,
            contentDescription = songName,
            modifier = Modifier
                .size(72.dp)
                .clip(
                    RoundedCornerShape(12.dp)
                ),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = songName,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = artistName,
                style = MaterialTheme.typography.bodyMedium,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }

        IconButton(
            onClick = onPreviewClick
        ) {
            Icon(
                imageVector = if (isPreviewPlaying) {
                    Icons.Default.Done
                } else {
                    Icons.Default.PlayArrow
                },
                contentDescription = if (isPreviewPlaying) {
                    "Önizlemeyi durdur"
                } else {
                    "Şarkıyı önizle"
                }
            )
        }
    }
}