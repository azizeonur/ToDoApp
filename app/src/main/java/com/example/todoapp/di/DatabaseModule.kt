package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.alarm.AlarmDao
import com.example.todoapp.data.alarm.AlarmSchedulerImpl
import com.example.todoapp.data.database.AppDatabase
import com.example.todoapp.data.entity.EntityDao
import com.example.todoapp.data.folder.FolderDao
import com.example.todoapp.data.note.NoteDao
import com.example.todoapp.domain.alarmUseCase.AlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "todoapp_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideEntityDao(
        database: AppDatabase
    ): EntityDao {
        return database.entityDao()
    }

    @Provides
    fun provideFolderDao(
        database: AppDatabase
    ): FolderDao {
        return database.folderDao()
    }

    @Provides
    fun provideNoteDao(
        database: AppDatabase
    ): NoteDao {
        return database.noteDao()
    }

    @Provides
    fun provideAlarmDao(
        database: AppDatabase
    ): AlarmDao {
        return database.alarmDao()
    }

    @Provides
    @Singleton
    fun provideAlarmScheduler(
        @ApplicationContext context: Context
    ): AlarmScheduler {
        return AlarmSchedulerImpl(context)
    }
}