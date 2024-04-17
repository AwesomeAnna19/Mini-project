package com.example.mini_project.ui.screens.quote

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mini_project.R
import com.example.mini_project.data.Screen
import com.example.mini_project.data.quote.Quote
import com.example.mini_project.ui.AppViewModelProvider
import com.example.mini_project.ui.navigation.NavRouteHandler

object QuoteRoute : NavRouteHandler {
    override val routeString = Screen.Quote.name
    override val topBarTitleResource = (R.string.quote)
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun QuoteScreen(
    viewModel: QuoteViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {

    val quoteUiState = viewModel.quoteUiState

    //(Sealed keyword, making the conditionals exhaustive - no else branch needed like usual with when statements
    when (quoteUiState) {
        is QuoteUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is QuoteUiState.Sucess -> SuccessScreen(quoteUiState.quote, modifier)
        is QuoteUiState.Error -> ErrorScreen(onRetry = onRetry, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun SuccessScreen(
    quote: Quote,
    modifier: Modifier = Modifier
    ) {

}

/**
 * The Quote screen displaying the loading picture.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {

    Image(
        modifier = modifier.size(250.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )

}

/**
 * The Quote screen displaying error message and retry button.
 */
@Composable
fun ErrorScreen(onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.ic_connection_error),
                contentDescription = stringResource(R.string.connection_error)
        )

        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))

        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry))
        }
    }
}
@Composable
fun ImageTextButtonScreen() {
    
}


