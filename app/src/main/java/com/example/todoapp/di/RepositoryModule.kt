package com.example.todoapp.di

import com.example.todoapp.data.alarm.AlarmRepository
import com.example.todoapp.data.alarm.AlarmRepositoryImpl
import com.example.todoapp.data.entity.EntityRepository
import com.example.todoapp.data.entity.EntityRepositoryImpl
import com.example.todoapp.data.folder.FolderRepository
import com.example.todoapp.data.folder.FolderRepositoryImpl
import com.example.todoapp.data.note.NoteRepository
import com.example.todoapp.data.note.NoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEntityRepository(
        repositoryImpl: EntityRepositoryImpl
    ): EntityRepository

    @Binds
    @Singleton
    abstract fun bindFolderRepository(
        repositoryImpl: FolderRepositoryImpl
    ): FolderRepository

    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        repositoryImpl: NoteRepositoryImpl
    ): NoteRepository

    @Binds
    @Singleton
    abstract fun bindAlarmRepository(
        repositoryImpl: AlarmRepositoryImpl
    ): AlarmRepository
}