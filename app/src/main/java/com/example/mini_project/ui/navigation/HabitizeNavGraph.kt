package com.example.mini_project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mini_project.data.Screen
import com.example.mini_project.ui.screens.FullScreen
import com.example.mini_project.ui.screens.home.HomeScreen
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
        composable(route = Screen.Tasks.name) {
           // HomeScreen()
        }
        composable(route = Screen.Stats.name) {
            FullScreen()
        }

        composable(route = Screen.Rewards.name) {
            TrophyScreenPreview()
        }

    }
}