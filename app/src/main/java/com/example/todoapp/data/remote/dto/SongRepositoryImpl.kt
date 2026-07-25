package com.example.todoapp.data.remote.dto

import com.example.todoapp.data.remote.api.JamendoApi
import com.example.todoapp.domain.song.Song
import com.example.todoapp.domain.song.SongRepository
import com.example.todoapp.domain.song.toDomain
import javax.inject.Inject
import javax.inject.Named

class SongRepositoryImpl @Inject constructor(
    private val api: JamendoApi,
    @param:Named("jamendo_client_id")
    private val clientId: String
) : SongRepository {

    override suspend fun getSongs(
        searchQuery: String,
        limit: Int
    ): List<Song> {
        return api.getTracks(
            clientId = clientId,
            search = searchQuery,
            limit = limit
        ).results.map { songDto ->
            songDto.toDomain()
        }
    }
}