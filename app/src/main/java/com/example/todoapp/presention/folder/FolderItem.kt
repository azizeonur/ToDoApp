package com.example.todoapp.presention.folder

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.data.database.RepeatType

@Composable
fun FolderItem(
    title: String,
    description: String?,
    selectedDate: String?,
    selectedTime: String?,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onDeleteClick: () -> Unit,
    shouldShake: Boolean = false,
    repeatType: RepeatType?,
    isCompleted: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val offsetX = remember { Animatable(0f) }

    val textDecoration = if (isCompleted) {
        TextDecoration.LineThrough
    } else {
        TextDecoration.None
    }

    val titleColor = if (isCompleted) {
        Color.LightGray
    } else {
        Color.White
    }

    LaunchedEffect(shouldShake) {
        if (shouldShake) {
            repeat(5) {
                offsetX.animateTo(-10f, animationSpec = tween(40))
                offsetX.animateTo(10f, animationSpec = tween(40))
            }
            offsetX.animateTo(0f, animationSpec = tween(40))
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF6C4AB6),
                shape = RoundedCornerShape(20.dp)
            )
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .offset(x = offsetX.value.dp)
            .padding(16.dp)
            .alpha(
                if (isCompleted) 0.5f else 1f
            )

    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = title,
                textDecoration = textDecoration,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "",
                    tint = Color.White
                )
            }

            Checkbox(
                checked = isCompleted,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.padding(start = 8.dp)

            )
        }

        Text(
            text = description.orEmpty(),
            textDecoration = textDecoration,
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
                text = when (repeatType) {
                    RepeatType.DAILY -> "Every day"
                    RepeatType.WEEKDAYS -> "Weekdays"
                    RepeatType.WEEKENDS -> "Weekends"
                    RepeatType.WEEKLY -> "Weekly"
                    RepeatType.MONTHLY -> "Monthly"
                    else -> selectedDate.orEmpty()
                },
                textDecoration = textDecoration,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                color = Color.Yellow,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = selectedTime ?: "",
                textDecoration = textDecoration,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                color = Color.Yellow,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

    }

}