package com.example.mini_project.data

import android.content.Context
import android.database.DatabaseUtils
import android.util.Log
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.example.mini_project.data.badge.Badge
import com.example.mini_project.data.badge.BadgeDao
import com.example.mini_project.data.category.Category
import com.example.mini_project.data.category.CategoryDao
import com.example.mini_project.data.task.Task
import com.example.mini_project.data.task.TaskDao

//Database annotation for the abstract class directly below parameters
@Database (
    entities = [Task::class, Category::class, Badge:: class],   //List of all entities/tables we have
    version = 3,               //Whenever you change the schema of the database table, you have to increase the version number
    exportSchema = false       //To keep schema version history backups or not
)
abstract class OurDatabase : RoomDatabase() {

    //The following functions return the Data Access Objects, so the database knows about them
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
    abstract fun badgeDao(): BadgeDao

    //Companion object allows access to methods to create or get the database
    // and uses the class name as the qualifier
    companion object{

        //Instance variable keeps a reference to the database, when one has been created.
        // helps maintain a single instance of the database opened at a given time
        @Volatile
        private var Instance: OurDatabase? = null

        /**
         * Thread-safe method for creating and accessing a singleton instance of the OurDatabase class using Room database.
         * Ensures that only one instance of the database is created and used throughout the application's lifecycle.
         */
        fun getDatabase(context: Context): OurDatabase{
            Log.e("Database", "GotDataBase")
            // If the Instance is not null, return it, otherwise create new database instance in synchronized block
                    // Synchronized block -> only one thread of execution at a time can enter this block of code
                    // -> makes sure the database only gets initialized once, avoids race condition
            return Instance ?: synchronized<OurDatabase>(this) {

                // Builds a Room database instance using the input application context and database class
                // Database name is "app_database". CHANGE IT!
                Room.databaseBuilder(context, OurDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration()
                    //.fallbackToDestructiveMigration()//REVISIT THIS! DESTROYS & REBUILDS the database, which means that the task data is lost. Migration strategy required, when schema changes what happens?
                    .build()  //Creates the database instance
                    .also { Instance = it; Log.e("Database", "Initialized") }  //Keeps a reference to the recently created db instance.
            }
        }
    }
}