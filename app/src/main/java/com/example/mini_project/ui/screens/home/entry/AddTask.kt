package com.example.mini_project.ui.screens.home.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.mini_project.R
import com.example.mini_project.data.task.Categories
import com.example.mini_project.data.task.Frequency
import com.example.mini_project.data.task.Task

@Composable
fun AddTaskFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_task)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskBottomSheet(
    viewModel: TaskEntryViewModel,
    sheetScaffoldState: BottomSheetScaffoldState,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

   BottomSheetScaffold(
       modifier = modifier,
       scaffoldState = sheetScaffoldState,
       sheetContent = {
           BottomSheetContent(
               taskUiState = viewModel.taskUiState,
               onTaskValueChange = viewModel::updateUiState,
               onCancel = onCancel, 
               onSubmit = onSubmit,
               modifier = modifier
           )
       }
   ) {
       content()
   }

}

@Composable
fun BottomSheetContent(
    taskUiState: TaskUiState,
    onTaskValueChange: (TaskDetails) -> Unit,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,

) {
    Column {
        TaskSheetHeader(headerTitle = "Add a task")
        TaskInputForm(
            taskDetails = taskUiState.taskDetails,
            onValueChange = onTaskValueChange,
            onDismiss = onCancel,
            dismissButtonLabel = stringResource(R.string.cancel),
            isSubmitButtonEnabled = taskUiState.isEntryValid,
            onSubmit = onSubmit,
            submitButtonLabel = stringResource(R.string.add_task)
        )
        
        Spacer(modifier = modifier.fillMaxHeight(0.2f))// Gives the spacer, enough height to clear bottom bar
    }
    
}



/**
 * The Header of the bottom Sheet, excluding the input part
 */
@Composable
fun TaskSheetHeader(modifier: Modifier = Modifier, headerTitle: String) {
    Text(
        text = headerTitle,
        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small)),
        textAlign = TextAlign.Center
    )
}


/**
 * The Input part of the bottom Sheet, excluding the header
 */
@Composable
fun TaskInputForm(
    taskDetails: TaskDetails,
    onValueChange: (TaskDetails) -> Unit ={},
    onDismiss: () -> Unit,
    dismissButtonLabel: String,
    isSubmitButtonEnabled: Boolean,
    onSubmit: () -> Unit,
    submitButtonLabel: String,
    modifier: Modifier = Modifier
) {
    Column (modifier.padding(dimensionResource(R.dimen.padding_small))) {
        TextInputRow(
            inputLabel = stringResource(R.string.title),
            fieldValue = taskDetails.title,
            onValueChange = { onValueChange(taskDetails.copy(title = it))}//viewmodel Logik
        )

        DropdownInputRow<Categories>(
            inputLabel = stringResource(R.string.category)
        )

        DropdownInputRow<Frequency>(
            inputLabel = stringResource(R.string.frequency)
        )


        SliderInputRow(
            inputLabel = stringResource(R.string.difficulty),
            value = taskDetails.difficulty,
            onValueChange = { onValueChange(taskDetails.copy(difficulty = it))},//view modelLogik
            minValue = 1,
            maxValue = 10
        )

        ButtonRow(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onDismiss = onDismiss,
            dismissButtonLabel = dismissButtonLabel,
            onSubmit = onSubmit,
            isSubmitButtonEnabled = isSubmitButtonEnabled, //viewmodel Logik!!!
            submitButtonLabel = submitButtonLabel
        )
    }
}

/**
 * Text input row for task Title and possibly Description.
 */
@Composable
fun TextInputRow(
    inputLabel: String,
    fieldValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    InputRow(inputLabel = inputLabel, modifier = modifier ) {
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = fieldValue,
            onValueChange = onValueChange,
            singleLine = true,
            //color,
            //keyboardOptions
        )
    }

}

/**
 * Dropdown Input Row for task Category and Frequency that are enums.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T : Enum<T>> DropdownInputRow(
    inputLabel: String,
    modifier: Modifier = Modifier
) {
    val optionsArray = enumValues<T>().map { option ->
        option.name
    }

    var selectedText by remember {
        mutableStateOf(optionsArray[0])
    }

    var isExpanded by remember {
        mutableStateOf(false)
    }

    InputRow(inputLabel = inputLabel, modifier = modifier) {
        
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                optionsArray.forEachIndexed { index, text ->

                    DropdownMenuItem(
                        text = { Text(text = text) },
                        onClick = {
                            selectedText = optionsArray[index]
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}
/** Slider input row for task Difficulty.
 */
@Composable
fun SliderInputRow(
    inputLabel: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    minValue: Int,
    maxValue: Int,
    modifier: Modifier = Modifier
) {
    val steps = (maxValue - minValue)
    InputRow(inputLabel = inputLabel, modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        )  {
            Slider(
                value = value.toFloat(),
                onValueChange = {onValueChange(it.toInt())},
                valueRange = minValue.toFloat()..maxValue.toFloat(),
                steps = steps,
                modifier = Modifier.fillMaxWidth()
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in minValue..maxValue) {
                    Text(
                        text = "$i",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/** Button row to cancel or add/edit task
*/
@Composable
fun ButtonRow(
    onDismiss: () -> Unit,
    dismissButtonLabel: String,
    onSubmit: () -> Unit,
    isSubmitButtonEnabled: Boolean,
    submitButtonLabel: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(bottom = dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large))
    ) {
        OutlinedButton(
            onClick = onDismiss,
            //border = null?
        ) {
            Text(text = dismissButtonLabel)
        }
        
        Button(
            onClick = onSubmit,
            enabled = isSubmitButtonEnabled
        ) {
            Text(text = submitButtonLabel )
        }
    }
}

/**
 * Input Row that contains the input's text label, and a box to contain the input field based on the type
 */
@Composable
fun InputRow(
    inputLabel: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row (
        modifier = modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = inputLabel,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
                .weight(1f)
                .padding(end = dimensionResource(R.dimen.padding_small))
        )

        Box(modifier = Modifier.weight(3f)) {
            content()
        }
    }
}

@Preview
@Composable
fun InputRowPreview() {
    MaterialTheme {
        TextInputRow(
            inputLabel = "Task Title",
            fieldValue = "Example Title",
            onValueChange = { /* Handle value change */ }
        )
    }
}

@Preview
@Composable
fun DropdownInputRowPreview() {
    MaterialTheme {

    }
}



