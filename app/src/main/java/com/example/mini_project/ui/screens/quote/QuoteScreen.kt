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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mini_project.R
import com.example.mini_project.data.Screen
import com.example.mini_project.data.quote.Quote
import com.example.mini_project.ui.AppViewModelProvider
import com.example.mini_project.ui.navigation.NavRouteHandler
import com.example.mini_project.ui.screens.home.entry.ButtonRow


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

    Column {

        QuoteOfTheDay(quote = quote.q, author = quote.a )

        Button(
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = stringResource(R.string.app_name)
            )
        }
    }
}

/**
 * The Quote screen displaying the loading picture.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {

   Column {
       ImageMessageColumn(
           modifier = modifier.size(20.dp),
           imageRes = R.drawable.loading_img,
           contentDescriptionRes = R.string.loading,
           messageRes = R.string.loading
       )

       Button(
           onClick = { /*TODO*/ }
       ) {
           Text(
               text = stringResource(R.string.skip)
           )

       }

   }
}

/**
 * The Quote screen displaying error message and retry button.
 */
@Composable
fun ErrorScreen(onRetry: () -> Unit, modifier: Modifier = Modifier) {

    Column {
        ImageMessageColumn(
            imageRes = R.drawable.ic_connection_error,
            contentDescriptionRes = R.string.connection_error,
            messageRes = R.string.loading_failed)

        ButtonRow(
            onDismiss = { /*TODO*/ },
            dismissButtonLabel = stringResource(R.string.retry),
            onSubmit = { /*TODO*/ },
            isSubmitButtonEnabled = true,
            submitButtonLabel = stringResource(R.string.skip)
        )

    }
}



@Composable
fun QuoteOfTheDay(quote: String, author: String) {

    //The actual quote of the day
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = quote,
            fontSize = 40.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(dimensionResource(R.dimen.padding_large)),
        )

        Text(
            text = author,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = 48.dp, end = 48.dp)
                .align(Alignment.End),
        )
    }
}

@Composable
fun ImageMessageColumn(
    imageRes: Int,
    contentDescriptionRes: Int,
    messageRes: Int,
    modifier: Modifier = Modifier) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier,
            painter = painterResource(imageRes),
            contentDescription = stringResource(contentDescriptionRes)
        )

        Text(
            text = stringResource(messageRes),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}


@Preview
@Composable
fun PreviewSuccessScreen() {
    // Create a sample quote for preview
    val sampleQuote = Quote(
        q = "Sample quote text",
        a = "Sample author",
        h = "jgsdj"
    )
    // Preview the SuccessScreen with the sample quote
    SuccessScreen(quote = sampleQuote)
}



