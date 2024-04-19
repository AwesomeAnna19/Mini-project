package com.example.mini_project.ui.screens.home

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_project.data.SavedDateDataStore
import com.example.mini_project.data.category.CategoriesRepository
import com.example.mini_project.data.task.Frequency
import com.example.mini_project.data.task.Task
import com.example.mini_project.data.task.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.util.Calendar


/*
* Holds and exposes the state the UI consumes.
* ViewModel stores the app-related data that isn't destroyed when the activity is destroyed and recreated by Android framework.
* Unlike the activity instance, ViewModel objects are not destroyed.
* The app automatically retains ViewModel objects during configuration changes
 so that the data they hold is immediately available after the recomposition.
 */

/*
Any changes to the UI state are immediately reflected in the UI
 */
@OptIn(ExperimentalMaterialApi::class)
class HomeViewModel(private val tasksRepository: TasksRepository, private val categoryRepository: CategoriesRepository, private val savedDateDataStore: SavedDateDataStore): ViewModel() {

    val homeUiState : StateFlow<HomeUiState> = combine(
        tasksRepository.getTaskByFrequencyList(Frequency.Daily),
        tasksRepository.getTaskByFrequencyList(Frequency.Weekly),
        tasksRepository.getTaskByFrequencyList(Frequency.Monthly),
        tasksRepository.getTaskByFrequencyList(Frequency.Yearly)
    ) { day, week, month, year ->
        HomeUiState(mapOf(
            Frequency.Daily to day,
            Frequency.Weekly to week,
            Frequency.Monthly to month,
            Frequency.Yearly to year)
        )}.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )
    fun CheckTime() {

    viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val time = Calendar.getInstance()

            val newDate = arrayOf(
                time.get(Calendar.DAY_OF_MONTH),
                time.get(Calendar.WEEK_OF_YEAR),
                time.get(Calendar.MONTH),
                time.get(Calendar.YEAR)
            )
            val savedDates = savedDateDataStore.SavedDate.first()
            val frequenciesToRenew = arrayOf(false, false, false, false)
            savedDates.forEachIndexed { i, d ->
                if (d != newDate[i]) {
                    frequenciesToRenew[i] = true
                }

                if (frequenciesToRenew.contains(true)) {
                    savedDateDataStore.SaveDate(newDate[0], newDate[1], newDate[2], newDate[3])
                }

            }

            frequenciesToRenew.forEachIndexed { i, r ->
                if (r) {
                    homeUiState.value.taskMap[Frequency.entries[i]]?.forEach {
                        setFinishTask(it, false)
                    }
                }
            }
        }


    }

    }


    fun setFinishTask(task: Task, status: Boolean = true) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.d(null, task.category.toString())
                tasksRepository.updateTask(task.copy(isDone = status, streak = task.streak + 1))
                val relevantCat = categoryRepository.getCategoryFromName(task.category).first()
                if (relevantCat == null) {Log.e(null, "Null relevant category: ${task.category}"); return@withContext}
                var newXp = relevantCat.currentXp + task.difficulty
                if (newXp >= relevantCat.xpRequiredForLevelUp) {
                    Log.d(null, "Levelled up")
                    newXp -= relevantCat.xpRequiredForLevelUp
                    categoryRepository.updateCategory(
                        relevantCat.copy(
                            currentXp = newXp,
                            currentLevel = relevantCat.currentLevel + 1
                        )
                    )
                } else categoryRepository.updateCategory(relevantCat.copy(currentXp = newXp))
            }
        }
    }

    fun InsertTask(task: Task) {
        viewModelScope.launch {
            tasksRepository.insertTask(task)
        }
    }

    /*fun test(): StateFlow<List<Task>> {
        Log.e("DatabaseStuff", "Test started")
        //container.__dataBase.clearAllTables()
        //container.tasksRepository.insertTask(Task(1, "Work", 5, Frequency.Weekly, 4, Categories.Health))
        val testBadges = container.badgesRepository.getBadgeList()
        val testCategory = container.categoriesRepository.listOfAllCategoriesSortedByCurrentLevel()
        return container.tasksRepository.getTaskByFrequencyList().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )

    }*/

    /*
    companion object {
        val factory:ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as OurApplication)
                HomeViewModel(application.container)
            }
        }
    }
   */




}


data class HomeUiState(
    val taskMap: Map<Frequency, List<Task>> = mapOf()
)
