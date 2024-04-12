package com.example.mini_project.ui.screens.trophy

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mini_project.R
import com.example.mini_project.ui.AppViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Preview(showSystemUi = true)
@Composable
fun TrophyScreenPreview (
    modifier: Modifier = Modifier,
    viewModel: TrophyViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val trophyUiState by viewModel.trophyUiState.collectAsState()

    TrophyLazyGrid(
        image = painterResource(id = R.drawable.trophy),
        achievementText = "Achievement"
    )
}

@Composable
fun TrophyLazyGrid (
    image: Painter,
    achievementText: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val itemsList = (0..17).toList()

    Column {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = modifier.padding(horizontal = 4.dp),
            contentPadding = contentPadding
        ){
            items(itemsList) {
                Image(
                    painter = painterResource(id = R.drawable.trophy),
                    contentDescription = toString(),
                    modifier.clickable {  }
                )
                Text(
                    text = "Achievement",
                    modifier = modifier
                        .padding(start = 20.dp, top = 130.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}



@Composable
fun TrophyItems() {

}
