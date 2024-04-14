package com.example.mini_project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mini_project.data.Screen
import com.example.mini_project.ui.screens.FullScreen
import com.example.mini_project.ui.screens.home.details.TaskDetailsScreen
import com.example.mini_project.ui.screens.trophy.TrophyScreenPreview

/**
 *
 *
 */

@Composable
fun HabitizeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Tasks.name,
        modifier = Modifier
    ) {

        //Til tasks hjemme skærm
        composable(route = Screen.Tasks.name) {
          /* HomeScreen(
              navigateToTaskEntry = {navController.navigate()},
              navigateToTaskUpdate = {
                   navController.navigate("")
               }
           )

           */
        }

        //Til Tasks detailed skærm
        composable(route = Screen.TaskDetails.name) {
            TaskDetailsScreen(
                navigateBack = {navController.navigateUp()}
            )
        }


        //Til stats skærm
        composable(route = Screen.Stats.name) {
            FullScreen()
        }

        //Til Rewards Skærm
        composable(route = Screen.Rewards.name) {
            TrophyScreenPreview()
        }

    }
}