package com.example.mini_project.ui.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
import com.example.mini_project.ui.screens.GraphScreen.StatsRoute
import com.example.mini_project.ui.screens.HabitizeBottomBar
import com.example.mini_project.ui.screens.HabitizeTopBar
import com.example.mini_project.ui.screens.home.entry.AddTaskBottomSheet
import com.example.mini_project.ui.screens.home.entry.AddTaskFAB
import com.example.mini_project.ui.screens.home.entry.TaskEntryViewModel
import com.example.mini_project.ui.screens.navItemList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//list of ting

//Card daily, weekly

//inventory row

object HomeRoute : NavRouteHandler {
    override val routeString = Screen.Tasks.name
    override val topBarTitleResource = R.string.app_name
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    navigateToTaskDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    taskEntryViewModel: TaskEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)

) {
    val homeUiState by homeViewModel.homeUiState.collectAsState()
    val windowUiState by homeViewModel.windowUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope() //Revisit

    homeViewModel.CheckTime()


    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )

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
                      coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }

                    }
                 )
        },
        bottomBar = {
            HabitizeBottomBar(
                navItemList = navItemList,
                navController = navController,
                defaultTab = HomeRoute.routeString,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ){ contentPadding ->
        AddTaskBottomSheet(
            viewModel = taskEntryViewModel,
            sheetScaffoldState = bottomSheetScaffoldState,
            onCancel = {
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.hide()
                }
                homeViewModel.toggleFABToState(true)
            },
            onSubmit = {
                coroutineScope.launch {
                    taskEntryViewModel.saveTask()
                    bottomSheetScaffoldState.bottomSheetState.hide()
                }
            }
        ) {

            HomeBody(
                uiState = homeUiState,
                viewModel = homeViewModel,
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
            , horizontalArrangement = Arrangement.Absolute.SpaceAround
            ,verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = false, onCheckedChange = {viewModel.setFinishTask(task)})

            Text(text = task.title, overflow = TextOverflow.Clip, softWrap = true, modifier = Modifier.weight(2f))

            Spacer(modifier = Modifier.weight(0.5f))

            Text(text = "XP: ${task.difficulty}", overflow = TextOverflow.Clip, softWrap = true, modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.weight(0.5f))

            Text(text = task.category.name, overflow = TextOverflow.Clip, softWrap = true, modifier = Modifier.weight(1f))
        }
    }

}





