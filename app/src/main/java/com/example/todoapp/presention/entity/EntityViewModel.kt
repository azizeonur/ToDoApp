package com.example.todoapp.presention.entity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.entity.EntityEntity
import com.example.todoapp.domain.entityUseCase.DeleteEntityUseCase
import com.example.todoapp.domain.entityUseCase.GetAllEntitiesUseCase
import com.example.todoapp.domain.entityUseCase.InsertEntityUseCase
import com.example.todoapp.domain.entityUseCase.UpdateEntityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntityViewModel @Inject constructor(
    private val getAllEntitiesUseCase: GetAllEntitiesUseCase,
    private val insertEntityUseCase: InsertEntityUseCase,
    private val updateEntityUseCase: UpdateEntityUseCase,
    private val deleteEntityUseCase: DeleteEntityUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EntityUiState())
    val uiState = _uiState.asStateFlow()

    fun onTitleChange(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun onDescriptionChange(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun showDialog(show: Boolean) {
        _uiState.value = _uiState.value.copy(showDialog = show)
    }

    fun showDeleteDialog(entity: EntityEntity?) {
        _uiState.value = _uiState.value.copy(
            showDeleteDialog = entity != null,
            entityToDelete = entity
        )
    }

    val entities: StateFlow<List<EntityEntity>> =
        getAllEntitiesUseCase()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun dismissDialog() {
        _uiState.value = _uiState.value.copy(
            showDialog = false,
            editingEntity = null,
            title = "",
            description = ""
        )
    }

    fun showEditDialog(entity: EntityEntity) {
        _uiState.value = _uiState.value.copy(
            showDialog = true,
            editingEntity = entity,
            title = entity.title,
            description = entity.description
        )
    }

    fun insertEntity(title: String, description: String) {
        viewModelScope.launch {
            insertEntityUseCase(title, description)
        }
    }

    fun updateEntity(entity: EntityEntity) {
        viewModelScope.launch {
            updateEntityUseCase(entity)
        }
    }

    fun deleteEntity(entity: EntityEntity) {
        viewModelScope.launch {
            deleteEntityUseCase(entity)
        }
    }
}