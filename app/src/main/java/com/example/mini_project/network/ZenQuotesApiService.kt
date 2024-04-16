package com.example.mini_project.network

import com.example.mini_project.data.quote.Quote
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


//base URL for the web service
private const val BASE_URL =
    "https://zenquotes.io/api"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


/** Defines how Retrofit talks to the web server using HTTP requests.
 */
interface ZenQuotesApiService {

    // Annotation tells Retrofit, this is a GET request. Specify "random" endpoint appended to base url
    @GET("random")
    suspend fun getRandomQuote(): Quote
}


/**
 * Public object to initialize the Retrofit service.
 * This object is the public singleton object that the rest of the app can access.
 */
object ZenQuotesApi {
    /** Lazily initialized retrofit object property named retrofitService of the type ZenQuotesApiService.
     * Meaning initialized at first usage to avoid unnecessary computation or use of other computing resources
     */
    val retrofitService: ZenQuotesApiService by lazy {

        //Initialize the retrofitService variable using the retrofit.create() method with the ZenQuotesApiService interface.
        retrofit.create(ZenQuotesApiService::class.java)
    }
}