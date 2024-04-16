package com.example.mini_project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mini_project.data.Screen
import com.example.mini_project.ui.screens.GraphScreen.FullScreen
import androidx.navigation.navArgument
import com.example.mini_project.ui.screens.GraphScreen.StatsRoute
import com.example.mini_project.ui.screens.home.HomeRoute
import com.example.mini_project.ui.screens.home.HomeScreen
import com.example.mini_project.ui.screens.home.details.TaskDetailsRoute
import com.example.mini_project.ui.screens.home.details.TaskDetailsScreen

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
        startDestination = HomeRoute.routeString,
        modifier = modifier
    ) {

        //Til tasks hjemme skærm
        composable(route = HomeRoute.routeString) {
          HomeScreen(
              navController = navController,
              navigateToTaskDetails = { navController.navigate("${TaskDetailsRoute.routeString}/$it")}
          )
        }

        //Til Tasks detailed skærm
        composable(
            route = TaskDetailsRoute.routeStringWithArguments,
            arguments = listOf(
                navArgument(TaskDetailsRoute.taskIdArgument) {
                    type = NavType.IntType
                }
            )
        ) {
            TaskDetailsScreen(
                navController = navController,
                navigateBack = { navController.popBackStack()},
                onNavigateUp = { navController.navigateUp()},
            )
        }

        //Til stats skærm
        composable(route = StatsRoute.routeString) {
            FullScreen()
        }

    }
}