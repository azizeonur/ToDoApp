package com.example.todoapp.presention.entity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.presention.compenent.DeleteDialogComponent

@Composable
fun EntityList(
    onEntityClick: (Int) -> Unit,
    viewModel: EntityViewModel = hiltViewModel()
) {
    val entities by viewModel.entities.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFA689E1))
    ) {
        Column {
            Text(
                text = "Entity List",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6C4AB6),
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.CenterHorizontally)
            )
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(entities) { entity ->

                    EntityCard(
                        title = entity.title,
                        subTitle = entity.description,
                        onClick = {
                            onEntityClick(entity.id)
                        },
                        onLongClick = {
                            viewModel.showDeleteDialog(entity)
                        },
                        onDeleteClick = {
                            viewModel.showDeleteDialog(entity)
                        },
                        onEditClick = {
                            viewModel.showEditDialog(entity)
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(56.dp)
                .background(
                    color = Color(0xFFE6DDFF),
                    shape = CircleShape
                )
                .clickable {
                    viewModel.showDialog(true)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "",
                tint = Color(0xFF7E57C2),
                modifier = Modifier.size(28.dp)
            )
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
            uiState.entityToDelete?.let {
                viewModel.deleteEntity(it)
            }
        }
    )
    if (uiState.showDialog) {
        EntityDialog(
            uiState = uiState,
            onTitleChange = viewModel::onTitleChange,
            onDescriptionChange = viewModel::onDescriptionChange,
            onDismiss = {
                viewModel.dismissDialog()
            },
            onSave = {

                val title = uiState.title.trim()
                val description = uiState.description.trim()

                if (title.isBlank() || description.isBlank()) {
                    viewModel.dismissDialog()
                }

                val editingEntity = uiState.editingEntity

                if (editingEntity == null) {

                    viewModel.insertEntity(
                        title = title,
                        description = description
                    )

                } else {

                    viewModel.updateEntity(
                        editingEntity.copy(
                            title = title,
                            description = description
                        )
                    )
                }

                viewModel.dismissDialog()
            }
        )
    }
}