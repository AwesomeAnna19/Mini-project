package com.example.mini_project.ui.screens.trophy

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import com.example.mini_project.R

@Preview(showSystemUi = true)
@Composable
fun TrophyScreenPreview (
    modifier: Modifier = Modifier,
) {
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

/*Dén her code snippet nedenunder er til popup window - den er ikke færdig endnu*/
private fun PopUpWindowAchievementDetails() {
    val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val popupView = inflater.inflate(R.layout.mainpopup, null)

    val width = ViewGroup.LayoutParams.MATCH_PARENT
    val height = ViewGroup.LayoutParams.WRAP_CONTENT
    val focusable: Boolean = true
    val popupWindow = PopupWindow(popupView,width,height,focusable)

    popupWindow.showAtLocation(layout,Gravity.BOTTOM,0,0)
}