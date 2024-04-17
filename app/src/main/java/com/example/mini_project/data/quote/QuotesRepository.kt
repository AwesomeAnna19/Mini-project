package com.example.mini_project.data.quote

import com.example.mini_project.network.ZenQuotesApiService

/** Repository retrieves quote data from underlying data source. */
interface QuotesRepository {

    /** Abstract function, which returns a quote object. Called from a coroutine, so declared with suspend.*/
    suspend fun getRandomQuote(): List<Quote>
}

/** Network Implementation of repository that retrieves quote data from underlying data source.*/
class NetworkQuotesRepository(
    private val zenQuotesApiService: ZenQuotesApiService
) : QuotesRepository {

    // override the abstract function getRandomQuote().
    /** Returns a quote from underlying data source by calling ZenQuotesApi.retrofitService.getRandomQuote()*/
    override suspend fun getRandomQuote(): List<Quote> = zenQuotesApiService.getRandomQuote()
}
