package com.example.mini_project.ui.screens.home.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.mini_project.R
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
    //Viewmodel: ,
    sheetScaffoldState: BottomSheetScaffoldState,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    submitButtonLabel: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {

   // val juice by juiceTrackerViewModel.currentJuiceStream.collectAsState()
   BottomSheetScaffold(
       modifier = modifier,
       scaffoldState = sheetScaffoldState,
       sheetContent = {
           BottomSheetContent(
               task, onCancel, onSubmit, submitButtonLabel
           )
       }
   ) {
       content()}
}

@Composable
fun BottomSheetContent(
    task: Task,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        TaskSheetHeader(headerTitle = "Add a task")
        TaskInputForm(
            task = task,
            onCancel = onCancel,
            onSubmit = onSubmit,
            submitButtonLabel = "Add Task"
        )
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
    //taskUiState
    task: Task,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    submitButtonLabel: String,
    modifier: Modifier = Modifier
) {
    Column {
        TextInputRow(
            inputLabel = stringResource(R.string.title),
            fieldValue = task.title,
            onValueChange = //Logik
        )
        DropdownInputRow(
            inputLabel = stringResource(R.string.category),
            fieldValue = task.category,
            onValueChange = //Logik
        )

        DropdownInputRow(
            inputLabel = stringResource(R.string.frequency),
            fieldValue = task.frequency ,
            onValueChange = //Logik
        )

        SliderInputRow(
            inputLabel = stringResource(R.string.difficulty),
            value = task.difficulty,
            onValueChange = ,//Logik
            minValue = 1,
            maxValue = 10
        )

        ButtonRow(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onCancel = onCancel,
            onSubmit = onSubmit,
            isSubmitButtonEnabled = , //Logik
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
            modifier = Modifier.fillMaxWidth(),
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
@Composable
fun DropdownInputRow(
    inputLabel: String,
    fieldValue: Enum<*>,
    onValueChange: (Enum<*>) -> Unit,
    modifier: Modifier = Modifier
) {
    InputRow(inputLabel = inputLabel, modifier = modifier) {
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = false,
            onDismissRequest = { /*TODO*/ },
            content = {
                fieldValue.javaClass.enumConstants?.forEach { value ->
                    DropdownMenuItem(
                        text = { value.toString() },
                        onClick = {onValueChange(value)})
                }
            }
        )
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
    onCancel: () -> Unit,
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
            onClick = onCancel,
            //border = null?
        ) {
            Text(text = stringResource(R.string.cancel))
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
        modifier = modifier,//.padding(bottom = dimensionResource(R.dimen.padding_small))
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = inputLabel,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
                .weight(1f)
                //.padding(end = dimensionResource(R.dimen.padding_small))
        )

        Box(modifier = Modifier.weight(2f)) {
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
        DropdownInputRow(
            inputLabel = "Task Frequency",
            fieldValue = Frequency.Daily,
            onValueChange = { /* Handle value change */ }
        )
    }
}



