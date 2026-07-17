package com.example.todoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.data.alarm.AlarmDao
import com.example.todoapp.data.alarm.AlarmEntity
import com.example.todoapp.data.entity.EntityDao
import com.example.todoapp.data.entity.EntityEntity
import com.example.todoapp.data.folder.FolderDao
import com.example.todoapp.data.folder.FolderEntity
import com.example.todoapp.data.note.NoteDao
import com.example.todoapp.data.note.NoteEntity

@Database(
    entities = [FolderEntity::class, NoteEntity::class, AlarmEntity::class, EntityEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun folderDao(): FolderDao
    abstract fun noteDao(): NoteDao
    abstract fun alarmDao(): AlarmDao
    abstract fun entityDao(): EntityDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "todoapp_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}