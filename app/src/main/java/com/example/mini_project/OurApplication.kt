package com.example.mini_project

import android.app.Application
import com.example.mini_project.data.AppContainer
import com.example.mini_project.data.AppDataContainer

/**
* Connects the application object to the application container.
*/
class OurApplication: Application() {

        /**
         * AppContainer instance used by the rest of classes to obtain dependencies.
         * Stores the AppDataContainer object.
         * Marked with lateinit modifier, as it will only be initialized during onCreate() call
         */
    lateinit var container: AppContainer


        // Inside the onCreate() method (called when the application is starting),
        // an instance of AppDataContainer is created and assigned to the container property.
        // Ensures "container" is initialized with the necessary dependencies during app startup.
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(context = this)
    }
}