package com.example.todoapp.song

import com.example.todoapp.domain.song.GetSongsUseCase
import com.example.todoapp.domain.song.Song
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class GetSongsUseCaseTest {

    private lateinit var fakeRepository: FakeSongRepository
    private lateinit var getSongsUseCase: GetSongsUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeSongRepository()

        getSongsUseCase = GetSongsUseCase(
            repository = fakeRepository
        )
    }

    @Test
    fun `getSongs should return songs from repository`() =
        runTest {

            val expectedSongs = listOf(
                Song(
                    id = "1",
                    name = "Believer",
                    duration = 204,
                    artistName = "Imagine Dragons",
                    imageUrl = "image_url",
                    audioUrl = "audio_url"
                ),
                Song(
                    id = "2",
                    name = "Hello",
                    duration = 295,
                    artistName = "Adele",
                    imageUrl = "image_url",
                    audioUrl = "audio_url"
                )
            )

            fakeRepository.songsToReturn = expectedSongs

            val result = getSongsUseCase(
                searchQuery = "rock",
                limit = 30
            )

            assertEquals(
                expectedSongs,
                result
            )
        }

    @Test
    fun `getSongs should return empty list when repository has no songs`() =
        runTest {

            fakeRepository.songsToReturn =
                emptyList()

            val result = getSongsUseCase(
                searchQuery = "rock",
                limit = 30
            )

            assertTrue(
                result.isEmpty()
            )
        }
}