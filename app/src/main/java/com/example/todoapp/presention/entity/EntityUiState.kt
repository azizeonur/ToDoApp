package com.example.todoapp.presention.entity

import com.example.todoapp.data.entity.EntityEntity

data class EntityUiState(
    val showDialog: Boolean = false,
    val title: String = "",
    val description: String = "",
    val showDeleteDialog: Boolean = false,
    val entityToDelete: EntityEntity? = null,
    val editingEntity: EntityEntity? = null
)