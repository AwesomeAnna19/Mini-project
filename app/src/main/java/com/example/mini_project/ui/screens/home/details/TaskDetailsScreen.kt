package com.example.mini_project.ui.screens.home.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mini_project.R
import com.example.mini_project.ui.AppViewModelProvider
import com.example.mini_project.ui.screens.HabitizeTopBar
import com.example.mini_project.ui.screens.home.entry.TaskInputForm


@Composable
fun TaskDetailsScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TaskDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    //val taskDetailsUiState

    Scaffold(
        topBar = {
            HabitizeTopBar(
                title = stringResource(R.string.edit_task),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->

        TaskInputForm(
            task = //taskDetailsUiState.value,
            onCancel = { /*TODO viewmodel*/ },
            onSubmit = { /*TODO viewmodel*/ },
            submitButtonLabel = stringResource(R.string.save),
            modifier = Modifier.padding(innerPadding)
        )
    }
}