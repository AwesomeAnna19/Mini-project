package com.example.mini_project.ui.screens.quote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_project.data.quote.Quote
import com.example.mini_project.data.quote.QuotesRepository
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException


/**
 * UI state for the Quote screen.
 * Loading - app is waiting for data.
 * Success - data was successfully retrieved from the web service.
 * Error - any network or connection errors.
 */

// Using sealed interface, to represent these three states.
// A sealed interface makes it easy to manage state by limiting the possible values.
// Restrict the quoteUiState web response to three states (data class objects): loading, success, and error,
sealed interface QuoteUiState {

    //Success state - receive quote information from server.
    // To store the data, add a constructor parameter to the Success data clas
    data class Success(val quote: List<Quote>) : QuoteUiState

     // Loading, Error states, don't need to set new data and create new objects;
     // They are just passing the web response.
     // Instead of data class, use Object to create objects for the web responses.
    object Error : QuoteUiState
    object Loading : QuoteUiState
}
class QuoteViewModel (private val quotesRepository: QuotesRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var quoteUiState: QuoteUiState by mutableStateOf(QuoteUiState.Loading) //Default value is loading
        private set //setter is made private to protect writes to the quoteUiState

    /**
     * Call getRandomQuote() on init to display status immediately.
     */
    init {
        getRandomQuote()
    }

    /**
     * Gets Mars photos information from the ZenQuotes API Retrofit service and updates the
     * [Quote].
     */
    fun getRandomQuote() {
        // Launch the coroutine using viewModelScope.launch to make the web service request in the background.
        // viewModelScope belongs to the ViewModel, the request continues even if the app goes through a configuration change.
        viewModelScope.launch {

            quoteUiState = QuoteUiState.Loading

            //Exception Handling to avoid termination without notifying user. Potential issues:
                // URL or URI used in the API is incorrect
                // server is unavailable, app couldn't connect to it.
                //A network latency issue.
                //Poor or no internet connection on the device.

            quoteUiState = try {            // Inside try block, add the code where you anticipate an exception.
                QuoteUiState.Success(quotesRepository.getRandomQuote())
            } catch (e: IOException) {      //In catch block, implement code that prevents abrupt termination of app. Recovers from error.
                QuoteUiState.Error
            } catch (e: HttpException) {
                QuoteUiState.Error
            }
        }
    }
}

