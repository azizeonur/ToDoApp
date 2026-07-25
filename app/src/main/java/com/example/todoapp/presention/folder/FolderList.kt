package com.example.todoapp.presention.folder

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.R
import com.example.todoapp.presention.compenent.DeleteDialogComponent


@Composable
fun FolderList(
    onFolderClick: (Int) -> Unit,
    onAddNoteClick: () -> Unit,
    notificationFolderId: Int = -1,
    viewModel: FolderViewModel = hiltViewModel()
) {

    val folders by viewModel.folders.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA689E1))
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(folders) { folder ->
                val lastNote = folder.notes.lastOrNull()
                val note = lastNote?.note
                val alarm = lastNote?.alarms?.firstOrNull()

                FolderItem(
                    title = note?.title.orEmpty().take(12),
                    description = note?.content.orEmpty().take(12),
                    onClick = {
                        onFolderClick(folder.folder.id)
                    },
                    onLongClick = {
                        viewModel.showDeleteDialog(folder.folder)
                    },
                    onDeleteClick = {
                        viewModel.showDeleteDialog(folder.folder)
                    },
                    isCompleted = note?.isCompleted ?: false,
                    onCheckedChange = { checked ->
                        note?.let {
                            viewModel.toggleCompleted(it, checked)
                        }
                    },
                    selectedDate = note?.selectedDate,
                    selectedTime = note?.selectedTime,
                    repeatType = alarm?.repeatType,
                    shouldShake = folder.folder.id == notificationFolderId,
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.BottomEnd),
            onClick = {
                onAddNoteClick()
            }
        ) {
            Text(stringResource(R.string.add_note))
        }
    }

    DeleteDialogComponent(
        show = uiState.showDeleteDialog,
        title = stringResource(R.string.delete_folder_title),
        message = stringResource(R.string.delete_folder_message),
        onDismiss = {
            viewModel.showDeleteDialog(null)
        },
        onConfirm = {
            uiState.folderToDelete?.let {
                viewModel.deleteFolder(it)
            }
        }
    )
}
