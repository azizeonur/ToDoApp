package com.example.todoapp.data.entity

import kotlinx.coroutines.flow.Flow

interface EntityRepository {

    fun getAllEntities(): Flow<List<EntityEntity>>

    suspend fun insertEntity(title: String, description: String): Long

    suspend fun updateEntity(entity: EntityEntity)

    suspend fun deleteEntity(entity: EntityEntity)
}