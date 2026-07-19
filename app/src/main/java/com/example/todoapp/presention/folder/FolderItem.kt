package com.example.todoapp.presention.folder

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FolderItem(
    title: String,
    description: String?,
    selectedDate: String?,
    selectedTime: String?,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF6C4AB6),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                onClick()
            }
            .padding(16.dp)
    ) {

        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = description.orEmpty(),
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.SemiBold,
            color = Color.Yellow,
            modifier = Modifier.padding(top = 8.dp)
        )

        Row(
            modifier = Modifier.padding(top = 8.dp),
        ) {
            Text(
                text = selectedDate ?: "",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                color = Color.Yellow,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = selectedTime ?: "",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                color = Color.Yellow,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

    }

}