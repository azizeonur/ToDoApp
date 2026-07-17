package com.example.todoapp.domain.entityUseCase

import com.example.todoapp.data.entity.EntityEntity
import com.example.todoapp.data.entity.EntityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllEntitiesUseCase @Inject constructor(
    private val repository: EntityRepository
) {
    operator fun invoke(): Flow<List<EntityEntity>> = repository.getAllEntities()
}