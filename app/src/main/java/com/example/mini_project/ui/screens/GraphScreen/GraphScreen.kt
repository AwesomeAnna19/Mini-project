package com.example.mini_project.ui.screens.GraphScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mini_project.R
import com.example.mini_project.data.Screen
import com.example.mini_project.ui.navigation.NavRouteHandler
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mini_project.data.category.Category
import com.example.mini_project.data.task.Task
import com.example.mini_project.ui.AppViewModelProvider
import com.example.mini_project.ui.screens.HabitizeBottomBar
import com.example.mini_project.ui.screens.HabitizeTopBar
import com.example.mini_project.ui.screens.home.HomeBody
import com.example.mini_project.ui.screens.home.HomeRoute
import com.example.mini_project.ui.screens.home.entry.AddTaskFAB
import com.example.mini_project.ui.screens.home.myTaskList
import com.example.mini_project.ui.screens.navItemList
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
import com.patrykandpatrick.vico.core.model.columnSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.math.RoundingMode
import com.patrykandpatrick.vico.core.model.ExtraStore
import kotlinx.coroutines.launch
import java.lang.NullPointerException

object StatsRoute : NavRouteHandler {
    override val routeString = Screen.Stats.name
    override val topBarTitleResource = R.string.stats_screen_title
}
@Composable
fun FullScreen(
    viewModel: GraphViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController
) {
    val uiState by viewModel.graphUiState.collectAsState()
    val windowUiState by viewModel.graphWindowUIState.collectAsState()


    Scaffold(
        topBar = {
            HabitizeTopBar(
                title = stringResource(HomeRoute.topBarTitleResource),
                canNavigateBack = false,
            )
        },
        bottomBar = {
            HabitizeBottomBar(
                navItemList = navItemList,
                navController = navController,
                defaultTab = StatsRoute.routeString,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) { contentPadding ->
        GraphBody(uiState = uiState, viewModel = viewModel, windowUiState = windowUiState, contentPadding)
    }

}

@Composable
fun GraphBody(
    uiState: GraphUiState,
    viewModel: GraphViewModel,
    windowUiState: GraphWindowUIState,
    paddingValues: PaddingValues

) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .background(color = Color.LightGray)
            .padding(paddingValues)
            .consumeWindowInsets(paddingValues)
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
            windowUiState,
            uiState,
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f)
        )

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
fun TopThreeList(graphWindowUIState: GraphWindowUIState, uiState: GraphUiState, modifier:Modifier = Modifier) {

    Column (
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .border(1.dp, color = Color.Black)
            .fillMaxSize(1f)){
        repeat(3) {
            if (graphWindowUIState.showingCategories) {
                if (it >= uiState.categoryList.count() || uiState.categoryList.isEmpty()) return
                TopThreeCard(uiState.categoryList[it], modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(1f))
            }
            else{
                if (it >= uiState.taskList.count() || uiState.taskList.isEmpty()) return
                TopThreeCard(uiState.taskList[it], modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(1f))
            }
            if (it < 2) {
                HorizontalDivider()
            }
        }
    }

}

@Composable
fun TopThreeCard(task : Task, modifier: Modifier) {

    Card (shape = RectangleShape, modifier = modifier) {
        Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize(1f)){
            Text(text = "${task.title}", modifier = Modifier.padding(4.dp))
            Text(text = "Streak: ${task.streak}", modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun TopThreeCard(category : Category, modifier: Modifier) {

    Card (shape = RectangleShape,modifier = modifier.fillMaxSize(1f)) {
        Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = modifier){
            Text(text = "${category.name.toString()}", modifier = Modifier.padding(4.dp))
            Text(text = "Level: ${category.currentLevel}", modifier = Modifier.padding(4.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GraphScreenPreview() {

    //()

}