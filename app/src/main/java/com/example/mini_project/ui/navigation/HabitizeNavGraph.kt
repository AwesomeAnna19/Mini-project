package com.example.mini_project.ui.navigation

import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mini_project.data.Screen
import com.example.mini_project.ui.screens.FullScreen
import com.example.mini_project.ui.screens.home.HomeScreen
import com.example.mini_project.ui.screens.home.details.TaskDetailsScreen
import com.example.mini_project.ui.screens.trophy.TrophyScreenPreview
import kotlinx.coroutines.launch
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.mini_project.ui.screens.StatsRoute
import com.example.mini_project.ui.screens.home.HomeRoute
import com.example.mini_project.ui.screens.home.details.TaskDetailsRoute

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
        composable(route = HomeRoute.routeString) {
          HomeScreen(
              onTabPressed = { screen ->  navController.navigate(screen.name)},
              ourUiState = ,
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
                navigateBack = { navController.popBackStack()},
                onNavigateUp = { navController.navigateUp()}
            )
        }

        //Til stats skærm
        composable(route = StatsRoute.routeString) {
            FullScreen()
        }

    }
}