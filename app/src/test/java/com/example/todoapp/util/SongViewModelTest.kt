package com.example.todoapp.util


import com.example.todoapp.domain.song.GetSongsUseCase
import com.example.todoapp.domain.song.Song
import com.example.todoapp.presention.song.SongViewModel
import com.example.todoapp.presention.song.SongsUiState
import com.example.todoapp.song.FakeSongRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class SongViewModelTest {

    @get:Rule
    val mainDispatcherRule =
        MainDispatcherRule()

    private lateinit var fakeRepository:
            FakeSongRepository

    private lateinit var getSongsUseCase:
            GetSongsUseCase

    private lateinit var viewModel:
            SongViewModel

    @Before
    fun setUp() {
        fakeRepository =
            FakeSongRepository()

        getSongsUseCase =
            GetSongsUseCase(
                repository = fakeRepository
            )

        viewModel =
            SongViewModel(
                getSongsUseCase = getSongsUseCase
            )
    }
    @Test
    fun `search state should change from loading to success`() =
        runTest(
            mainDispatcherRule.testDispatcher
        ) {

            val expectedSongs = listOf(
                Song(
                    id = "1",
                    name = "Believer",
                    duration = 204,
                    artistName = "Imagine Dragons",
                    imageUrl = "image_url",
                    audioUrl = "audio_url"
                )
            )

            fakeRepository.songsToReturn =
                expectedSongs

            fakeRepository.responseDelayMillis =
                1_000L

            runCurrent()

            viewModel.onSearchQueryChange("rock")

            advanceTimeBy(500)
            runCurrent()

            val loadingState =
                viewModel.uiState.value

            assertTrue(
                loadingState is SongsUiState.Loading
            )

            assertEquals(
                1,
                fakeRepository.getSongsCallCount
            )

            advanceTimeBy(1_000)
            runCurrent()

            val successState =
                viewModel.uiState.value

            assertTrue(
                successState is SongsUiState.Success
            )

            assertEquals(
                expectedSongs,
                (successState as SongsUiState.Success).songs
            )
        }
    @Test
    fun `rapid search changes should send only the latest query`() =
        runTest(
            mainDispatcherRule.testDispatcher
        ) {

            runCurrent()

            viewModel.onSearchQueryChange("ro")

            advanceTimeBy(200)

            viewModel.onSearchQueryChange("roc")

            advanceTimeBy(200)

            viewModel.onSearchQueryChange("rock")

            advanceTimeBy(499)
            runCurrent()

            assertEquals(
                0,
                fakeRepository.getSongsCallCount
            )

            advanceTimeBy(1)
            runCurrent()

            assertEquals(
                1,
                fakeRepository.getSongsCallCount
            )

            assertEquals(
                "rock",
                fakeRepository.lastSearchQuery
            )
        }

    @Test
    fun `search should call repository only after 500 milliseconds`() =
        runTest(
            mainDispatcherRule.testDispatcher
        ) {

            fakeRepository.songsToReturn =
                emptyList()

            runCurrent()

            viewModel.onSearchQueryChange("rock")


            advanceTimeBy(499)
            runCurrent()

            assertEquals(
                0,
                fakeRepository.getSongsCallCount
            )

            advanceTimeBy(1)
            runCurrent()

            assertEquals(
                1,
                fakeRepository.getSongsCallCount
            )
        }

    @Test
    fun `search should not call repository when query is shorter than two characters`() =
        runTest(
            mainDispatcherRule.testDispatcher
        ) {


            viewModel.onSearchQueryChange("r")

            advanceTimeBy(500)
            runCurrent()
            advanceUntilIdle()

            assertEquals(
                0,
                fakeRepository.getSongsCallCount
            )
        }

    @Test
    fun `search should return error when repository fails`() =
        runTest(
            mainDispatcherRule.testDispatcher
        ) {


            fakeRepository.shouldThrowError = true
            fakeRepository.errorMessage =
                "Şarkılar alınamadı"

            viewModel.onSearchQueryChange(
                "rock"
            )

            advanceTimeBy(500)
            runCurrent()
            advanceUntilIdle()

            val state =
                viewModel.uiState.value

            assertTrue(
                state is SongsUiState.Error
            )

            val errorState =
                state as SongsUiState.Error

            assertEquals(
                "Şarkılar alınamadı",
                errorState.message
            )

            assertEquals(
                1,
                fakeRepository.getSongsCallCount
            )
        }
    @Test
    fun `search should return success after debounce`() =
        runTest(
            mainDispatcherRule.testDispatcher
        ) {


            val expectedSongs =
                listOf(
                    Song(
                        id = "1",
                        name = "Believer",
                        duration = 204,
                        artistName =
                            "Imagine Dragons",
                        imageUrl = "image_url",
                        audioUrl = "audio_url"
                    )
                )

            fakeRepository.songsToReturn =
                expectedSongs

            viewModel.onSearchQueryChange(
                "rock"
            )

            advanceTimeBy(500)
            runCurrent()
            advanceUntilIdle()


            val state =
                viewModel.uiState.value

            assertTrue(
                state is SongsUiState.Success
            )

            val successState =
                state as SongsUiState.Success

            assertEquals(
                expectedSongs,
                successState.songs
            )

            assertEquals(
                1,
                fakeRepository.getSongsCallCount
            )

            assertEquals(
                "rock",
                fakeRepository.lastSearchQuery
            )
        }
}