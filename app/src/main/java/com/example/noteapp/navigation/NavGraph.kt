package com.example.noteapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.example.noteapp.Screen
import com.example.noteapp.Screen.HomeScreen
import com.example.noteapp.screen.HomeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.noteapp.screen.NoteScreen

@Composable
fun NavGraph(navController: NavHostController,paddingValues: PaddingValues){

    NavHost(navController=navController,startDestination= Screen.HomeScreen.route){

      composable(route=Screen.HomeScreen.route){
          HomeScreen(
              navController=navController,
              modifier = Modifier.padding(paddingValues)
          )
      }

        composable(
            route = "${Screen.NoteScreen.route}/{noteId}?",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.StringType
                    defaultValue = "" // Default empty value
                },

            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: ""


            NoteScreen(
                navController = navController,
                modifier = Modifier.padding(paddingValues),
                noteId = noteId,

            )
        }


    }



}