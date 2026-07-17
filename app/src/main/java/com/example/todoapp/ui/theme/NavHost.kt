package com.example.todoapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapp.presention.entity.EntityList
import com.example.todoapp.presention.folder.FolderList
import com.example.todoapp.presention.note.ListScreen


@Composable
fun ToDoAppNavHost(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Router.ENTITY,
        modifier = modifier
    ) {

        composable(route = Router.ENTITY) {

            EntityList(
                onEntityClick = { entityId ->
                    navController.navigate("${Router.FOLDER}/$entityId")
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
        ) {

            FolderList(
                onFolderClick = { folderId ->
                    navController.navigate("${Router.LIST}/$folderId")
                }
            )

        }

        composable(
            route = "${Router.LIST}/{folderId}",
            arguments = listOf(
                navArgument("folderId") {
                    type = NavType.IntType
                }
            )
        ) {

            ListScreen(
                onBack = {
                    navController.popBackStack()
                }
            )

        }
    }

}