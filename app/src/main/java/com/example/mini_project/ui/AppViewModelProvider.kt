package com.example.mini_project.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mini_project.OurApplication
import com.example.mini_project.ui.screens.home.HomeViewModel
import com.example.mini_project.ui.screens.home.details.TaskDetailsViewModel
import com.example.mini_project.ui.screens.trophy.TrophyViewModel


/**
 * Provides Factory to create instance of ViewModel for the entire app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(ourApplication().container/*.tasksRepository*/)
        }

        // Initializer for TrophyMainScreen
        initializer {
            TrophyViewModel(ourApplication().container)
        }

        // Initializer for TaskDetailsViewModel
        initializer {
            TaskDetailsViewModel(ourApplication().container)
        }
    }
}

fun CreationExtras.ourApplication(): OurApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as OurApplication)