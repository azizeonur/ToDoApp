package com.example.todoapp.data.database

import androidx.room.Embedded
import androidx.room.Relation
import com.example.todoapp.data.alarm.AlarmEntity
import com.example.todoapp.data.folder.FolderEntity
import com.example.todoapp.data.note.NoteEntity

data class FolderWithNotes(
    @Embedded val folder: FolderEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "folderId"
    )
    val notes: List<NoteEntity>
)

data class NoteWithAlarms(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "noteId"
    )
    val alarms: List<AlarmEntity>
)