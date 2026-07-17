package com.example.todoapp.data.entity

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EntityRepositoryImpl @Inject constructor(private val entityDao: EntityDao) :
    EntityRepository {
    override fun getAllEntities(): Flow<List<EntityEntity>> {
        return entityDao.getAllEntities()
    }

    override suspend fun insertEntity(title: String): Long {
        return entityDao.insertEntity(EntityEntity(title = title))
    }

    override suspend fun updateEntity(entity: EntityEntity) {
        return entityDao.updateEntity(entity)
    }

    override suspend fun deleteEntity(entity: EntityEntity) {
        return entityDao.deleteEntity(entity)
    }
}