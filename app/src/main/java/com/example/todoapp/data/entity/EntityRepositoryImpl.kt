package com.example.todoapp.data.entity

import android.R.attr.description
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EntityRepositoryImpl @Inject constructor(private val entityDao: EntityDao) :
    EntityRepository {
    override fun getAllEntities(): Flow<List<EntityEntity>> {
        return entityDao.getAllEntities()
    }

    override suspend fun insertEntity(
        title: String,
        description: String
    ): Long {
        return entityDao.insertEntity(EntityEntity(title = title, description = description))
    }
    override suspend fun updateEntity(entity: EntityEntity) {
        return entityDao.updateEntity(entity)
    }

    override suspend fun deleteEntity(entity: EntityEntity) {
        return entityDao.deleteEntity(entity)
    }
}