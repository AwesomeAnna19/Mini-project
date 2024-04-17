package com.example.mini_project.network

import com.example.mini_project.data.quote.Quote
import retrofit2.http.GET


/** Defines how Retrofit talks to the web server using HTTP requests.
 */
interface ZenQuotesApiService {

    // Annotation tells Retrofit, this is a GET request. Specify "random" endpoint appended to base url
    @GET("random")
    suspend fun getRandomQuote(): Quote
}