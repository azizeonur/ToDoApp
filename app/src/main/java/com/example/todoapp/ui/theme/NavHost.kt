package com.example.todoapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapp.presention.entity.EntityList
import com.example.todoapp.presention.folder.FolderList
import com.example.todoapp.presention.note.ListScreen
import com.example.todoapp.presention.song.SongScreen
import com.example.todoapp.presention.song.SongSelectionViewModel


@Composable
fun ToDoAppNavHost(
    modifier: Modifier = Modifier,
    entityId: Int = -1,
    notificationFolderId: Int = -1
) {
    val navController = rememberNavController()

    val songSelectionViewModel: SongSelectionViewModel =
        hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Router.ENTITY,
        modifier = modifier
    ) {

        composable(Router.ENTITY) {
            EntityList(
                onEntityClick = { id ->
                    navController.navigate(
                        "${Router.FOLDER}/$id"
                    )
                }
            )
        }

        composable(
            route = "${Router.FOLDER}/{entityId}",
            arguments = listOf(
                navArgument("entityId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val currentEntityId =
                backStackEntry.arguments
                    ?.getInt("entityId")
                    ?: return@composable

            FolderList(
                onFolderClick = { folderId ->
                    navController.navigate(
                        "editNote/$folderId"
                    )
                },
                onAddNoteClick = {
                    navController.navigate(
                        "addNote/$currentEntityId"
                    )
                },
                notificationFolderId =
                    notificationFolderId
            )
        }

        composable(
            route = "addNote/{entityId}",
            arguments = listOf(
                navArgument("entityId") {
                    type = NavType.IntType
                }
            )
        ) {
            ListScreen(
                onBack = {
                    navController.popBackStack()
                },
                onSelectSong = {
                    navController.navigate(Router.SONGS)
                },
                selectionViewModel =
                    songSelectionViewModel
            )
        }

        composable(
            route = "editNote/{folderId}",
            arguments = listOf(
                navArgument("folderId") {
                    type = NavType.IntType
                }
            )
        ) {
            ListScreen(
                onBack = {
                    navController.popBackStack()
                },
                onSelectSong = {
                    navController.navigate(Router.SONGS)
                },
                selectionViewModel =
                    songSelectionViewModel
            )
        }

        composable(Router.SONGS) {
            SongScreen(
                onBack = {
                    navController.popBackStack()
                },
                selectionViewModel =
                    songSelectionViewModel
            )
        }
    }

    LaunchedEffect(entityId) {
        if (entityId != -1) {
            navController.navigate(
                "${Router.FOLDER}/$entityId"
            ) {
                launchSingleTop = true
            }
        }
    }
}