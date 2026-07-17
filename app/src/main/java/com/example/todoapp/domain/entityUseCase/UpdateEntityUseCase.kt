package com.example.todoapp.domain.entityUseCase

import com.example.todoapp.data.entity.EntityEntity
import com.example.todoapp.data.entity.EntityRepository
import javax.inject.Inject

class UpdateEntityUseCase @Inject constructor(private val repository: EntityRepository) {
    suspend operator fun invoke(entity: EntityEntity) {
        repository.updateEntity(entity)
    }

}