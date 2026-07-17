package com.example.todoapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entities")
data class EntityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String
)