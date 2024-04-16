package com.example.mini_project.ui.screens.quote

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mini_project.R
import com.example.mini_project.ui.theme.MiniprojectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiniprojectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QuoteMainScreen()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun QuoteMainScreen() {
    ShowQuoteOnStartingScreen()
    QuoteButton()
}

@Composable
fun ShowQuoteOnStartingScreen() {
    //The title with "Quote of the Day"
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.quote_of_the_day_title),
            modifier = Modifier
                .padding(top = 150.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
    }

    //The actual quote of the day
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.quote_of_the_day),
            modifier = Modifier
                .padding(top = 250.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineMedium,
            fontStyle = FontStyle.Italic
        )
    }
}

@Composable
fun QuoteButton() {
    val ytb = "https://www.youtube.com/"
    val context = LocalContext.current
    val yintent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(ytb)) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = { context.startActivity(yintent) },
            modifier = Modifier
                .height(100.dp)
                .width(200.dp)
                .padding(10.dp)
        ) {
            Text(
                text = "Enter the app",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}
