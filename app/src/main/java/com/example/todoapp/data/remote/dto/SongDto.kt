package com.example.todoapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SongDto(
    val id: String,
    val name: String,
    val duration: Int,

    @SerializedName("artist_name")
    val artistName: String,

    val image: String,
    val audio: String
)