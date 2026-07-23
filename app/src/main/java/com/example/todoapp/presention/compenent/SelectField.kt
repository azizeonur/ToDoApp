package com.example.todoapp.presention.compenent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp


@Composable
fun SelectField(
    title: String,
    value: String,
    placeholder: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {

    Column {

        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium
        )

        Spacer(modifier = Modifier.height(6.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(
                    if (enabled) 1f else 0.5f
                )
                .clickable (
                    enabled = enabled
                ){
                    onClick()
                },
            tonalElevation = 2.dp,
            shadowElevation = 2.dp,
            shape = MaterialTheme.shapes.medium
        ) {

            Text(
                text = if (value.isBlank()) placeholder else value,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )

        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}