package com.example.mini_project.ui.screens.GraphScreen

import android.util.Log
import android.view.SurfaceControl.Transaction
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import co.yml.charts.common.extensions.isNotNull
import com.example.mini_project.data.badge.Badge
import com.example.mini_project.data.category.Category
import com.example.mini_project.data.task.Categories
import com.example.mini_project.ui.AppViewModelProvider
import com.example.mini_project.ui.screens.home.HomeViewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.axis.formatter.DecimalFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.model.CartesianChartModel
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ColumnCartesianLayerModel
import com.patrykandpatrick.vico.core.model.ExtraStore
import com.patrykandpatrick.vico.core.model.columnSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.Nullable
import java.lang.NullPointerException
import java.math.RoundingMode

@Composable
fun FullScreen(
    viewModel: GraphViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.graphUiState.collectAsState()
    val windowUiState by viewModel.graphWindowUIState.collectAsState()

    Log.e(null, "This many tasks in list: ${uiState.taskList.count()}")

    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize(1f)) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .background(color = Color.LightGray)
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight(0.08f)
                    .fillMaxWidth(1f)
                    .padding(2.dp)
            ) {
                BoxButton(
                    onClick = {viewModel.switchGraphViewed()},
                    text = "Show tasks",
                    enabled = windowUiState.showingCategories,
                    modifier = Modifier
                        .background(if (!windowUiState.showingCategories) Color.DarkGray else Color.Gray)
                        .fillMaxHeight(1f)
                        .fillMaxWidth(0.3f)
                )

                BoxButton(
                    onClick = {viewModel.switchGraphViewed()},
                    text = "Show categories",
                    enabled = !windowUiState.showingCategories,
                    modifier = Modifier
                        .background(if (windowUiState.showingCategories) Color.DarkGray else Color.Gray)
                        .fillMaxHeight(1f)
                        .fillMaxWidth(0.4f)
                )
            }


                //Spacer(modifier = Modifier.fillMaxHeight(0.01f))

                Card(
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxHeight(0.60f)
                        .border(1.dp, color = Color.Black)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxHeight(1f)

                    ) {
                        Card(
                            shape = RectangleShape,
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxHeight(1f)
                        ) {
                            GraphCard(
                                windowUiState,
                                viewModel
                            )
                        }

                    }
                }

                TopThreeList(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .fillMaxHeight(1f)
                )

            }
        }


    }

@Composable
fun BoxButton(text:String, onClick: () -> Unit, enabled:Boolean, modifier:Modifier) {

    Box(
        modifier = modifier
            .clickable(onClick = onClick, enabled = enabled),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, textAlign = TextAlign.Center)
    }
}




@Composable
fun GraphCard(windowUIState: GraphWindowUIState, viewModel: GraphViewModel) {
            Card(shape = RectangleShape, modifier = Modifier.padding(10.dp)) {


                val listKey = ExtraStore.Key<List<String>>()

                val modelProducer = if (windowUIState.showingCategories) {
                    viewModel.tryCategoryGraph(listKey = listKey)
                    }
                    else {
                        viewModel.tryTaskGraph(listKey = listKey)
                    }

                val formatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> {x, values, _ ->
                    try {
                        values.model.extraStore[listKey][x.toInt()]
                    }
                    catch (e: NullPointerException)
                    {
                        ""
                    }
                }
                    TimeLineGraph(modelProducer = modelProducer, formatter = formatter)

            }
}

@Composable
fun TopThreeList(modifier:Modifier) {

    Column (modifier = modifier
        .border(1.dp, color = Color.Black)
        .fillMaxSize(1f)){
        repeat(3) {
            TaskCard(modifier = modifier.weight(1f))
            if (it < 2) {
                HorizontalDivider()
            }
        }
    }

}


@Composable
fun TimeLineGraph(
    modelProducer: CartesianChartModelProducer,
    formatter: AxisValueFormatter<AxisPosition.Horizontal.Bottom> = DecimalFormatAxisValueFormatter<AxisPosition.Horizontal.Bottom>()
) {
    Log.d(null, "TimeLineGraphComposed")

    CartesianChartHost(chart = rememberCartesianChart(
        rememberColumnCartesianLayer(),
        startAxis = rememberStartAxis(),
        bottomAxis = rememberBottomAxis(valueFormatter = formatter)
        ),
        modelProducer,
        modifier = Modifier.fillMaxHeight(1f))

}

@Composable
fun TaskCard(/*task:Task,*/ modifier: Modifier) {

    Card (shape = RectangleShape,modifier = modifier.fillMaxSize(1f)) {
        Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = modifier){
            Text(text = "Task name", modifier = Modifier.padding(4.dp))
            Text(text = "Streak: x", modifier = Modifier.padding(4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GraphScreenPreview() {

    FullScreen()

}