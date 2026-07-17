package com.example.todoapp.presention.folder

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun FolderList(
    onFolderClick: (Int) -> Unit,
    viewModel: FolderViewModel = hiltViewModel()
) {

    val folders by viewModel.folders.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA689E1))
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(folders) { folder ->

                FolderItem(
                    title = folder.title,
                    description = folder.description,
                    onClick = {
                        onFolderClick(folder.id)
                    }
                )

            }

        }

    }


}

@Composable
fun FolderItem(
    title: String,
    description: String,
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
            text = description,
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.SemiBold,
            color = Color.Yellow,
            modifier = Modifier.padding(top = 8.dp)
        )

    }

}


@Preview(showBackground = true)
@Composable
fun FolderListPreview() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA689E1))
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(
                listOf(
                    "Android Projesi",
                    "İngilizce Çalışma",
                    "Spor"
                )
            ) { title ->

                FolderItem(
                    title = title,
                    description = "Bu klasörde 5 not var",
                    onClick = {}
                )

            }

        }

    }
}

