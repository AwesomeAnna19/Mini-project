package com.example.mini_project.ui.screens.quote


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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

@Composable
fun QuoteScreen(
    viewModel: QuoteViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onRetry: () -> Unit = viewModel::getRandomQuote,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier
) {

    val quoteUiState = viewModel.quoteUiState

    //(Sealed keyword, making the conditionals exhaustive - no else branch needed like usual with when statements
    when (quoteUiState) {
        is QuoteUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize(), onSkip = onSkip)
        is QuoteUiState.Success -> SuccessScreen(quoteUiState.quote, onSkip = onSkip)
        is QuoteUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize(), onRetry = onRetry, onSkip = onSkip,)
    }
}

/**
 * The Quote screen displaying the quote, and button to move on.
 */
@Composable
fun SuccessScreen(
    quote: List<Quote>,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier
) {
    QuoteOfTheDay(
        quote = quote.elementAt(0).quote,
        author = quote.elementAt(0).author,
        modifier = modifier
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSkip,
            modifier = modifier,
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
fun LoadingScreen(modifier: Modifier = Modifier, onSkip: () -> Unit) {

    ImageMessageColumn(
        modifier = modifier.size(20.dp),
        imageRes = R.drawable.loading_img,
        contentDescriptionRes = R.string.loading,
        messageRes = R.string.loading
    ) {
        Button(
           onClick = onSkip
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
fun ErrorScreen(onRetry: () -> Unit, onSkip: () -> Unit, modifier: Modifier = Modifier) {

    ImageMessageColumn(
        imageRes = R.drawable.ic_connection_error,
        contentDescriptionRes = R.string.connection_error,
        messageRes = R.string.loading_failed
    ) {
        ButtonRow(
            onDismiss = onRetry,
            dismissButtonLabel = stringResource(R.string.retry),
            onSubmit = onSkip,
            isSubmitButtonEnabled = true,
            submitButtonLabel = stringResource(R.string.skip)
        )
    }
}



@Composable
fun QuoteOfTheDay(
    quote: String?,
    author: String?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    //The actual quote of the day
    Column (
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = quote.toString(),
            fontSize = 40.sp,
            lineHeight = 50.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(dimensionResource(R.dimen.padding_large)),
        )

        Text(
            text = author.toString(),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(48.dp)
                .align(Alignment.End),
        )

        Box(modifier = modifier) {
            content()
        }
    }
}

@Composable
fun ImageMessageColumn(
    imageRes: Int,
    contentDescriptionRes: Int,
    messageRes: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

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

        Box(modifier = modifier){
            content()
        }
    }
}


@Preview
@Composable
fun SuccessScreenPreview() {
    // Create a sample quote for preview
    val sampleQuote = listOf(
        Quote(
            quote = "Sample quote text",
            author = "Sample author",
            htmlQuote = "jgsdj"
        )
    )

    // Preview the SuccessScreen with the sample quote
    SuccessScreen(quote = sampleQuote, onSkip = {})
}

