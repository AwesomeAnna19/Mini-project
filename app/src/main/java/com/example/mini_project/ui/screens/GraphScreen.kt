package com.example.mini_project.ui.screens

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
import com.example.mini_project.R
import com.example.mini_project.data.Screen
import com.example.mini_project.ui.navigation.NavRouteHandler
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.DecimalFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.model.CartesianChartModel
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ColumnCartesianLayerModel
import com.patrykandpatrick.vico.core.model.columnSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.math.RoundingMode

object StatsRoute : NavRouteHandler {
    override val routeString = Screen.Stats.name
    override val topBarTitleResource = R.string.stats_screen_title
}
@Composable
fun FullScreen() {
    //var ShowingTask by remember {mutableStateOf(false)}
    var showingCategory by remember {mutableStateOf(false)}

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
                    onClick = {showingCategory = false},
                    text = "Show tasks",
                    enabled = showingCategory,
                    modifier = Modifier
                        .background(if (!showingCategory) Color.DarkGray else Color.Gray)
                        .fillMaxHeight(1f)
                        .fillMaxWidth(0.3f)
                )

                BoxButton(
                    onClick = {showingCategory = true},
                    text = "Show categories",
                    enabled = !showingCategory,
                    modifier = Modifier
                        .background(if (showingCategory) Color.DarkGray else Color.Gray)
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
                                if (showingCategory) {
                                    createModelFromLists(listOf(1, 2, 3, 4, 5), listOf(1, 2, 3, 4, 5))
                                }
                                else
                                {
                                    createModelFromLists(listOf(2, 5, 3, 1, 9), listOf(6, 22, 5, 8, 10))
                                }
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
fun GraphCard(model: CartesianChartModel) {
            Card(shape = RectangleShape, modifier = Modifier.padding(10.dp)) {
                    TimeLineGraph(model)
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

fun createModelFromLists(x:List<Number>, y:List<Number>) : CartesianChartModel {

    return CartesianChartModel(
        ColumnCartesianLayerModel.build{
            series(x, y)
        }
    )

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


@Composable
fun TimeLineGraph(
    //modelProducer: CartesianChartModelProducer
    model:CartesianChartModel
) {

    val modelProducer = remember {CartesianChartModelProducer.build()}

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            while (isActive) {
                modelProducer.tryRunTransaction {
                    columnSeries {
                        series(x = listOf(1, 2, 3, 4, 5, 6, 2), y = listOf(2, 3, 4, 5, 6, 7, 8))
                    }
                }
                //delay(200)
            }
        }
    }

    val formatter = DecimalFormatAxisValueFormatter<AxisPosition.Vertical.Start>(roundingMode = RoundingMode.CEILING)
    CartesianChartHost(chart = rememberCartesianChart(
        rememberColumnCartesianLayer(),
        startAxis = rememberStartAxis(valueFormatter = formatter),
        bottomAxis = rememberBottomAxis()
        ),
        model,
        modifier = Modifier.fillMaxHeight(1f))

}

@Composable
fun GraphArea(
    modelProducer: CartesianChartModelProducer
) {

    CartesianChartHost(chart = rememberCartesianChart(
        rememberColumnCartesianLayer(),
        startAxis = rememberStartAxis(),
        bottomAxis = rememberBottomAxis(),
    ), modelProducer)

}

@Composable
fun templateChart() {
    val modelProducer = remember { CartesianChartModelProducer.build() }
    LaunchedEffect(Unit) { modelProducer.tryRunTransaction { columnSeries { series(4, 12, 8, 16) } } }
    CartesianChartHost(
        rememberCartesianChart(
            rememberColumnCartesianLayer(),
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(),
        ),
        modelProducer,
    )
}

@Preview(showBackground = true)
@Composable
fun GraphScreenPreview() {

    FullScreen()

}