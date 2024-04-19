package com.example.mini_project.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.util.Calendar

class SavedDateDataStore(
    val dataStore: DataStore<Preferences>
) {
    companion object {
        val DAY = intPreferencesKey("DAY")
        val WEEK = intPreferencesKey("WEEK")
        val MONTH = intPreferencesKey("MONTH")
        val YEAR = intPreferencesKey("YEAR")
    }

    val SavedDate : Flow<Array<Int>> = dataStore.data.map {date ->
        arrayOf((date[DAY] ?: 0), date[WEEK] ?: 0, date[MONTH] ?: 0, date[YEAR] ?: 0)
    }
    suspend fun SaveDate(day : Int, week : Int, month : Int, year: Int) {

        dataStore.edit {date ->
            date[DAY] = day
            date[WEEK] = week
            date[MONTH] = month
            date[YEAR] = year
        }

    }



}