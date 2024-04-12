package com.example.mini_project

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mini_project.ui.navigation.HabitizeNavHost

@Composable
fun OurApp(navController: NavHostController = rememberNavController()) {
    HabitizeNavHost(navController = navController)
}