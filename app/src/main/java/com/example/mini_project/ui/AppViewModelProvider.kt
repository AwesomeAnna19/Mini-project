package com.example.mini_project.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mini_project.OurApplication
import com.example.mini_project.ui.screens.home.HomeViewModel


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


    }
}

fun CreationExtras.ourApplication(): OurApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as OurApplication)