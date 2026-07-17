package com.example.todoapp.data.folder

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folders")
data class FolderEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val entityId: Int,

    val title: String,

    val description: String,

    val createdAt: Long = System.currentTimeMillis()
)