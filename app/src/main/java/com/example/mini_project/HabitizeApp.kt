package com.example.mini_project

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mini_project.ui.navigation.HabitizeNavHost

/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun HabitizeApp(navController: NavHostController = rememberNavController()) {
    HabitizeNavHost(navController = navController)
}