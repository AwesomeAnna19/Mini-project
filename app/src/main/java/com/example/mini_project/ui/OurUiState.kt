package com.example.mini_project.ui

import com.example.mini_project.data.Screen

data class OurUiState (
    val screens: List<Screen>,
    val currentScreen: Screen = Screen.Tasks,
)