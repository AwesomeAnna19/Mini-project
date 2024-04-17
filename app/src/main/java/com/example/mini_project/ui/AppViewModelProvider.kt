package com.example.mini_project.ui

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresExtension
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
import com.example.mini_project.data.quote.QuotesRepository


/**
 * Provides Factory to create instance of ViewModel for the entire app
 */
object AppViewModelProvider {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    val Factory = viewModelFactory {

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(ourApplication().container/*.tasksRepository*/)
        }

        // Initializer for TrophyMainScreen
        initializer {
            TrophyViewModel(ourApplication().container)
        }

        initializer {
            GraphViewModel(ourApplication().container)
        }

        // Initializer for TaskDetailsViewModel
        initializer {
            TaskDetailsViewModel(savedStateHandle = SavedStateHandle(), ourApplication().container)
        }

        initializer {
            QuoteViewModel(ourApplication().container.quotesRepository)
        }
    }
}

fun CreationExtras.ourApplication(): OurApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as OurApplication)