package com.example.todoapp.presention.entity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.entity.EntityEntity
import com.example.todoapp.domain.entityUseCase.DeleteEntityUseCase
import com.example.todoapp.domain.entityUseCase.GetAllEntitiesUseCase
import com.example.todoapp.domain.entityUseCase.InsertEntityUseCase
import com.example.todoapp.domain.entityUseCase.UpdateEntityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

    val entities: StateFlow<List<EntityEntity>> =
        getAllEntitiesUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Companion.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun insertEntity(title: String) {
        viewModelScope.launch {
            insertEntityUseCase(title)
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