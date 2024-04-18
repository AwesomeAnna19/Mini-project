package com.example.mini_project.ui

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mini_project.OurApplication
import com.example.mini_project.ui.screens.GraphScreen.GraphViewModel
import com.example.mini_project.ui.screens.home.HomeViewModel
import com.example.mini_project.ui.screens.home.details.TaskDetailsViewModel
import com.example.mini_project.ui.screens.quote.QuoteViewModel
import com.example.mini_project.ui.screens.trophy.TrophyViewModel

//RETTE SUMMARY: PERFEKT, HUSK DOG AT SLETTE TROPHY VIEWMODEL

/**
 * Provides Factory to create instance of ViewModel for the entire app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(ourApplication().container.tasksRepository)
        }

        // HUSK AT SLETTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initializer {
            TrophyViewModel(ourApplication().container)
        }

        // Initializer for GraphViewModel
        initializer {
            GraphViewModel(
                ourApplication().container.tasksRepository,
                ourApplication().container.categoriesRepository
                )
        }

        // Initializer for TaskDetailsViewModel
        initializer {
            TaskDetailsViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                ourApplication().container.tasksRepository)
        }

        // Initializer for QuoteViewModel
        initializer {
            QuoteViewModel(ourApplication().container.quotesRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [OurApplication].
 */
fun CreationExtras.ourApplication(): OurApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as OurApplication)