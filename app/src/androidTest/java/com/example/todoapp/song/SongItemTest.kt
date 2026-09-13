package com.example.todoapp.song

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.todoapp.presention.song.SongItem
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SongItemTest {

    @get:Rule
    val composeTestRule =
        createComposeRule()

    @Test
    fun songItem_shouldDisplaySongAndArtistName() {

        composeTestRule.setContent {
            MaterialTheme {
                SongItem(
                    songName = "Believer",
                    artistName = "Imagine Dragons",
                    imageUrl = "",
                    isPreviewPlaying = false,
                    onPreviewClick = {},
                    onClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Believer")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Imagine Dragons")
            .assertIsDisplayed()

    }

    @Test
    fun previewButton_shouldCallOnPreviewClick() {

        // Arrange
        var previewClicked = false

        composeTestRule.setContent {
            MaterialTheme {
                SongItem(
                    songName = "Believer",
                    artistName = "Imagine Dragons",
                    imageUrl = "",
                    isPreviewPlaying = false,
                    onPreviewClick = {
                        previewClicked = true
                    },
                    onClick = {}
                )
            }
        }

        // Act
        composeTestRule
            .onNodeWithContentDescription(
                "Şarkıyı önizle"
            )
            .performClick()

        // Assert
        assertTrue(previewClicked)
    }
}