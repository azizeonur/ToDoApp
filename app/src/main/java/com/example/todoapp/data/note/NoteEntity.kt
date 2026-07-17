package com.example.todoapp.data.note

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.todoapp.data.folder.FolderEntity

@Entity(
    tableName = "notes",
    foreignKeys = [ForeignKey(
        entity = FolderEntity::class,
        parentColumns = ["id"],
        childColumns = ["folderId"],
        onDelete = ForeignKey.Companion.CASCADE
    )],
    indices = [Index("folderId")]
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val folderId: Int,
    val title: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis()
)