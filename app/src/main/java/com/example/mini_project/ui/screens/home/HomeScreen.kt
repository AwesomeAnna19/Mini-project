package com.example.mini_project.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mini_project.R
import com.example.mini_project.data.Screen
import com.example.mini_project.data.category.Category
import com.example.mini_project.data.task.Categories
import com.example.mini_project.data.task.Frequency
import com.example.mini_project.data.task.Task
import com.example.mini_project.ui.AppViewModelProvider
import com.example.mini_project.ui.OurUiState
import com.example.mini_project.ui.screens.LifeRPGBottomBar
import com.example.mini_project.ui.screens.HabitizeTopBar
import com.example.mini_project.ui.screens.navItemList
import com.example.mini_project.ui.theme.MiniprojectTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



//list of ting

//Card daily, weekly

//inventory row


val myCategory = Category(name = "Health", color = "red", currentLevel = 1, currentXp = 0, xpRequiredForLevelUp = 100)
val myTask: Task = Task(title = "This is a task", difficulty = 1, frequency = Frequency.Monthly, streak = 0, category = Categories.Health)

val myTaskList = listOf(myTask, myTask, myTask)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onTabPressed: ((Screen)-> Unit),
    ourUiState: OurUiState
) {
    val homeUiState by viewModel.homeUiState.collectAsState()

    LaunchedEffect (Unit) {
        withContext(Dispatchers.IO) {
            viewModel.test()
        }
    }
    Scaffold(
        topBar = {
            HabitizeTopBar()
        },
        floatingActionButton = { AddTaskFAB(onClick = { /*TODO*/ }) },
        bottomBar = {
            LifeRPGBottomBar(
                currentTab = ourUiState.currentScreen ,
                onTabPressed = onTabPressed,
                navItemList = navItemList,
                modifier = Modifier
                    .fillMaxWidth()

            )
        }
    ) { contentPadding ->
            HomeBody(
                categoryTitles = "dude",
                categoryTaskList = myTaskList,
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
            )
    }
}
@Composable
fun HomeBody(
    categoryTitles: String,
    categoryTaskList: List<Task>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(1.dp)
    ) {
        items(items = categoryTaskList) {item ->
            CategoryTaskList(
                categoryTitle = categoryTitles,
                taskList = categoryTaskList,
                modifier = Modifier
            )
        }
    }


}
@Composable
fun CategoryTaskList(
    categoryTitle: String,
    taskList: List<Task>,
    modifier: Modifier = Modifier

    ) {

    Card (modifier = Modifier
        .padding(dimensionResource(R.dimen.padding_small))
    
    ) {
        Column(modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_small))

        ) {

            Text(
                text = categoryTitle,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.
                padding( dimensionResource(R.dimen.padding_medium)                )
            )

            TaskList(taskList = taskList )
        }
    }
}



@Composable
private fun TaskList(
    taskList: List<Task>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        taskList.forEach { task ->
            TaskRow(
                task = myTask,
                modifier = Modifier
                    .padding(1.dp)
            )
        }
    }
}


@Composable
fun TaskRow(
    task: Task,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Blue,
        ),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = false, onCheckedChange = {})

            Text(text = task.title)

            Spacer(modifier = Modifier.weight(1f))

            Text(text = "${task.difficulty}")

            Spacer(modifier = Modifier.weight(1f))

            Text(text = task.frequency.name)
        }
    }

}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    //HomeScreen(onTabPressed = ((Screen)-> Unit))
}

@Preview(showBackground = true)
@Composable
fun CategorizedTaskListPreview() {
    MiniprojectTheme {
        CategoryTaskList(categoryTitle = "title", taskList = myTaskList)
    }

}

@Preview(showBackground = true)
@Composable
fun TaskListPreview() {
    MiniprojectTheme {
        TaskList(
            myTaskList
        )

    }
}

@Preview(showBackground = true)
@Composable
    fun TaskRowPreview() {
        MiniprojectTheme {
            TaskRow(task = myTask)
        }
    }



