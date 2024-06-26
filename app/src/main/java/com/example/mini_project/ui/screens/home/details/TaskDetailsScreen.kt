package com.example.mini_project.ui.screens.home.details

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mini_project.R
import com.example.mini_project.data.Screen
import com.example.mini_project.ui.AppViewModelProvider
import com.example.mini_project.ui.navigation.NavRouteHandler
import com.example.mini_project.ui.screens.HabitizeBottomBar
import com.example.mini_project.ui.screens.HabitizeTopBar
import com.example.mini_project.ui.screens.home.HomeRoute
import com.example.mini_project.ui.screens.home.entry.TaskInputForm
import com.example.mini_project.ui.screens.navItemList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object TaskDetailsRoute : NavRouteHandler {
    override val routeString = Screen.TaskDetails.name
    override val topBarTitleResource = R.string.edit_task
    const val taskIdArgument = "taskId"
    val routeStringWithArguments = "$routeString/{${taskIdArgument}}"
}
@Composable
fun TaskDetailsScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: TaskDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val taskUiState by viewModel.taskUiState.collectAsState()

    Scaffold(
        topBar = {
            HabitizeTopBar(
                title = stringResource(TaskDetailsRoute.topBarTitleResource),
                canNavigateBack = true,
                navigateUp = onNavigateUp
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
        },
        modifier = modifier
    ) { innerPadding ->

        TaskInputForm(
            taskDetails = taskUiState.taskDetails,
            onValueChange = {task -> viewModel.updateUiState(task)  },
            onDismiss = {
                coroutineScope.launch {
                    viewModel.deleteTask()
                    navigateBack()
                }
            },
            dismissButtonLabel = stringResource(R.string.delete),
            isSubmitButtonEnabled = taskUiState.isEntryValid,
            onSubmit = {
                coroutineScope.launch {
                    viewModel.updateTask()
                    navigateBack()
                }
            },
            submitButtonLabel = stringResource(R.string.save),
            modifier = Modifier.padding(innerPadding)
        )
    }
}


