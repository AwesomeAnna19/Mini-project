package com.example.mini_project.ui.screens.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mini_project.R
import com.example.mini_project.data.Screen
import com.example.mini_project.data.category.Category
import com.example.mini_project.data.task.Categories
import com.example.mini_project.data.task.Frequency
import com.example.mini_project.data.task.Task
import com.example.mini_project.ui.AppViewModelProvider
import com.example.mini_project.ui.navigation.NavRouteHandler
import com.example.mini_project.ui.screens.HabitizeBottomBar
import com.example.mini_project.ui.screens.HabitizeTopBar
import com.example.mini_project.ui.screens.home.entry.AddTaskBottomSheet
import com.example.mini_project.ui.screens.home.entry.AddTaskFAB
import com.example.mini_project.ui.screens.navItemList
import com.example.mini_project.ui.theme.MiniprojectTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//list of ting

//Card daily, weekly

//inventory row


val myCategory = Category(name = Categories.Health, color = "red", currentLevel = 1, currentXp = 0, xpRequiredForLevelUp = 100)
val myTask: Task = Task(title = "This is a task", difficulty = 1, frequency = Frequency.Monthly, streak = 0, category = Categories.Health, isDone = false)

val myTaskList = listOf(myTask, myTask, myTask)


object HomeRoute : NavRouteHandler {
    override val routeString = Screen.Tasks.name
    override val topBarTitleResource = R.string.app_name
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController,
    navigateToTaskDetails: (Int) -> Unit
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    viewModel.InsertTask(myTask)

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )




    LaunchedEffect (Unit) {
        withContext(Dispatchers.IO) {
            viewModel.test()
            //bottomSheetScaffoldState.bottomSheetState.hide()
        }
    }
/*
    AddTaskBottomSheet(
        sheetScaffoldState = bottomSheetScaffoldState,
        onCancel = {
            coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.hide()
            }
        },
        onSubmit = {
            //viewModel.saveTask
            coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.hide()
            }
        }
    ) {
        Scaffold(
            topBar = {
                HabitizeTopBar(
                    title = stringResource(HomeRoute.topBarTitleResource),
                    canNavigateBack = false,
                    )
            },
            floatingActionButton = {
                AddTaskFAB(
                    onClick = {
                        // viewModel.resetCurrentTask
                        coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
                    }
                )
            },
            bottomBar = {
                HabitizeBottomBar(
                    navItemList = navItemList,
                    navController = navController,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        ) { contentPadding ->
            HomeBody(
                uiState = homeUiState,
                viewModel = viewModel,
                onTaskClick = navigateToTaskDetails,
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
            )
        }
    }
}

 */

    Scaffold(
        topBar = {
            HabitizeTopBar(
                title = stringResource(HomeRoute.topBarTitleResource),
                canNavigateBack = false,
            )
        },
        floatingActionButton = {
            AddTaskFAB(
                onClick = {
                    // viewModel.resetCurrentTask
                    coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
                }
            )
        },
        bottomBar = {
            HabitizeBottomBar(
                navItemList = navItemList,
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ){ contentPadding ->
        AddTaskBottomSheet(
            sheetScaffoldState = bottomSheetScaffoldState,
            onCancel = {
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.hide()
                }
            },
            onSubmit = {
                //viewModel.saveTask
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.hide()
                }
            }
        ) {
            HomeBody(
                categoryTitles = "dude",
                categoryTaskList = myTaskList,
                onTaskClick = navigateToTaskDetails,
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
            )
        }
    }
}


    @Composable
fun HomeBody(
    uiState: HomeUiState,
    viewModel: HomeViewModel,
    onTaskClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val fullMaps = uiState.taskMap.filter { x -> x.value.isNotEmpty() }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(1.dp)
    ) {
        items(items = fullMaps.toList()) {
            CategoryTaskList(
                content = it,
                viewModel = viewModel,
                onTaskClick = {onTaskClick(it.id)},
                modifier = Modifier
            )
        }
    }
}
@Composable
fun CategoryTaskList(
    content: Pair<Frequency, List<Task>>,
    viewModel: HomeViewModel,
    onTaskClick: (Task) -> Unit,
    modifier: Modifier = Modifier

    ) {

    Card (modifier = Modifier
        .padding(dimensionResource(R.dimen.padding_small))
    
    ) {
        Column(modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_small))

        ) {

            Text(
                text = content.first.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.
                padding( dimensionResource(R.dimen.padding_medium)                )
            )

            TaskList(
                content = content,
                viewModel = viewModel,
                onTaskClick = onTaskClick
            )
        }
    }
}



@Composable
private fun TaskList(
    content: Pair<Frequency, List<Task>>,
    viewModel: HomeViewModel,
    onTaskClick: (Task) -> Unit,
    modifier: Modifier = Modifier
) {


    Column(
        modifier = modifier,
    ) {
        content.second.forEach { task ->
            if ( !task.isDone) {
                TaskRow(
                    viewModel = viewModel,
                    task = task,
                    modifier = Modifier
                        .padding(1.dp)
                        .clickable { onTaskClick(task) }
                )
            }
        }
    }
}


@Composable
fun TaskRow(
    viewModel: HomeViewModel,
    task: Task,
    modifier: Modifier = Modifier
) {



    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = false, onCheckedChange = {viewModel.finishTask(task)})

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
        /*
        CategoryTaskList(categoryTitle = "title", taskList = myTaskList)

         */
    }

}

@Preview(showBackground = true)
@Composable
fun TaskListPreview() {
    MiniprojectTheme {
        /*
        TaskList(
            myTaskList
        )

         */

    }
}

@Preview(showBackground = true)
@Composable
    fun TaskRowPreview() {
        MiniprojectTheme {
            //TaskRow(task = myTask)
        }
    }



