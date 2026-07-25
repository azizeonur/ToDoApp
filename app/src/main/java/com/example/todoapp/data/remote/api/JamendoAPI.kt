package com.example.todoapp.data.remote.api

import com.example.todoapp.data.remote.dto.TracksResponseDto
import retrofit2.http.GET
import retrofit2.http.Query


interface JamendoApi {

    @GET("tracks/")
    suspend fun getTracks(
        @Query("client_id")
        clientId: String,
        @Query("format")
        format: String = "json",
        @Query("limit") limit: Int = 30,
        @Query("search") search: String

    ): TracksResponseDto
}