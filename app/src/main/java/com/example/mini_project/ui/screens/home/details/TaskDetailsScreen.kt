package com.example.mini_project.ui.screens.home.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mini_project.R
import com.example.mini_project.data.Screen
import com.example.mini_project.ui.AppViewModelProvider
import com.example.mini_project.ui.navigation.NavRouteHandler
import com.example.mini_project.ui.screens.HabitizeTopBar
import com.example.mini_project.ui.screens.home.entry.TaskInputForm
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
    viewModel: TaskDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            HabitizeTopBar(
                title = stringResource(TaskDetailsRoute.topBarTitleResource),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->

        TaskInputForm(
            task =, //viewmodel.taskUiState,
            onDismiss = {
                //viewModel.deleteTask
                navigateBack()
            },
            dismissButtonLabel = stringResource(R.string.delete),
            onSubmit = {
                coroutineScope.launch {
                    //viewmodel.updateTask()
                    navigateBack()
                }
            },
            submitButtonLabel = stringResource(R.string.save),
            modifier = Modifier.padding(innerPadding)
        )
    }
}