package com.example.todoapp.domain.entityUseCase

import com.example.todoapp.data.entity.EntityRepository
import javax.inject.Inject

class InsertEntityUseCase @Inject constructor(
    private val repository: EntityRepository
) {

    suspend operator fun invoke(title: String): Long {
        return repository.insertEntity(title)
    }
}