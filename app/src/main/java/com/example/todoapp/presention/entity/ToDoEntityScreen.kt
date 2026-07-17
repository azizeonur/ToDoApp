package com.example.todoapp.presention.entity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EntityList(
    onEntityClick: (Int) -> Unit,
    onAddClick: () -> Unit = {},
    viewModel: EntityViewModel = hiltViewModel()
) {
    val entities by viewModel.entities.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFA689E1))
    ) {
        Column {
            Text(
                text = "Entity List",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6C4AB6),
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.CenterHorizontally)
            )
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(entities) { entity ->
                    FolderCard(
                        title = entity.title,
                        subTitle = "",
                        modifier = Modifier.clickable {
                            onEntityClick(entity.id)
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(56.dp)
                .background(
                    color = Color(0xFFE6DDFF),
                    shape = CircleShape
                )
                .clickable {
                    showDialog = true
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color(0xFF7E57C2),
                modifier = Modifier.size(28.dp)
            )
        }
    }
    if (showDialog) {

        AlertDialog(

            onDismissRequest = {
                showDialog = false
            },

            title = {
                Text("Kategori Oluştur")
            },

            text = {

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    label = {
                        Text("Kategori Adı")
                    }
                )

            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showDialog = false
                        title = ""
                    }
                ) {
                    Text("İptal")
                }

            },

            confirmButton = {

                TextButton(
                    onClick = {

                        if (title.isNotBlank()) {

                            viewModel.insertEntity(title.trim())

                            title = ""
                            showDialog = false

                        }

                    }
                ) {

                    Text("Kaydet")

                }

            }

        )

    }
}
@Preview(showBackground = true)
@Composable
fun EntityListPreview() {

    val fakeEntities = listOf(
        "İş",
        "Okul",
        "Spor",
        "Kişisel"
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA689E1))
    ) {

        Column {

            Text(
                text = "Entity List",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6C4AB6),
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.CenterHorizontally)
            )


            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)

            ) {

                items(fakeEntities) { entity ->

                    FolderCard(
                        title = entity,
                        subTitle = "0 klasör",
                        modifier = Modifier
                    )

                }

            }
        }


        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(56.dp)
                .background(
                    Color(0xFFE6DDFF),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color(0xFF7E57C2)
            )

        }

    }

}

@Composable
fun FolderCard(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF6C4AB6)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE6DDFF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Folder",
                    tint = Color(0xFF7E57C2)
                )
            }

            Column {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subTitle,
                    fontSize = 14.sp,
                    color = Color.Yellow
                )
            }
        }
    }
}

