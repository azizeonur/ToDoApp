package com.example.todoapp.song

import com.example.todoapp.presention.song.SongSelectionViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test


class SongSelectionViewModelTest {

    private lateinit var viewModel: SongSelectionViewModel

    @Before
    fun setUp() {
        viewModel = SongSelectionViewModel()
    }

    @Test
    fun `selected song should initially be null`() {

        val result = viewModel.selectedSong.value

        assertNull(result)
    }

    @Test
    fun `selectSong should save song name and url`() {

        val expectedName = "Believer"
        val expectedUrl =
            "https://example.com/believer.mp3"

        viewModel.selectSong(
            songName = expectedName,
            songUrl = expectedUrl
        )

        val result =
            viewModel.selectedSong.value

        assertEquals(
            expectedName,
            result?.name
        )

        assertEquals(
            expectedUrl,
            result?.url
        )
    }
}