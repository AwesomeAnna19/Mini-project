package com.example.mini_project

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.core.model.CartesianChartModel
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ColumnCartesianLayerModel
import com.patrykandpatrick.vico.core.model.columnSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun TestScreem() {

    Column {
        Card (
            Modifier
                .fillMaxSize()
                .weight(1f)) {
            Text("Hello")
        }
        Card (
            Modifier
                .fillMaxSize()
                .weight(1f)){
            Text("Girl")
        }
        Card(
            Modifier
                .fillMaxSize()
                .weight(1f)) {
            Text(text = "What you doing?")
        }
    }

}

@Composable
fun FullScreen() {


    Column (verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize(1f)){
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight(0.8f)) {

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxHeight(0.08f)
                    .fillMaxWidth(1f)
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Gray)
                        .clickable { }
                        .fillMaxHeight(1f)
                        .fillMaxWidth(0.3f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Sort by category")
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            Card(
                modifier = Modifier
                    .fillMaxHeight(0.80f)
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
                        GraphCard()
                    }
                }
            }
        }
    }


}

@Composable
fun GraphCard() {
            Card(shape = RectangleShape, modifier = Modifier.padding(10.dp)) {
                    TimeLineGraph()
            }
}


@Composable
fun TimeLineGraph(
    //modelProducer: CartesianChartModelProducer
) {
        val premadeModel = CartesianChartModel(
        ColumnCartesianLayerModel.build{
            series(x = listOf(1, 2, 3, 4, 5, 6), y = listOf(2, 3, 4, 5, 6, 7))
        }
    )
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

    CartesianChartHost(chart = rememberCartesianChart(
        rememberColumnCartesianLayer(),
        startAxis = rememberStartAxis(),
        bottomAxis = rememberBottomAxis()
        ),
        premadeModel,
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