package com.example.mini_project.ui.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
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
import com.example.mini_project.ui.screens.quote.QuoteRoute
import com.example.mini_project.ui.screens.quote.QuoteScreen
import com.example.mini_project.ui.screens.quote.QuoteUiState
import com.example.mini_project.ui.screens.quote.QuoteViewModel

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
        startDestination = QuoteRoute.routeString,
        modifier = modifier
    ) {

        composable(route = QuoteRoute.routeString) {
            QuoteScreen(
                onSkip = {navController.navigate(HomeRoute.routeString)} )
        }

        //Til tasks hjemme skærm
        composable(route = HomeRoute.routeString) {
          HomeScreen(
              navController = navController,
              navigateToTaskDetails = { navController.navigate("${TaskDetailsRoute.routeString}/$it")}
          )
        }

        composable(route = StatsRoute.routeString) {
            FullScreen(navController = navController)
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


    }
}