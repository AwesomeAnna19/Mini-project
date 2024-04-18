package com.example.mini_project.data.quote

import com.google.gson.annotations.SerializedName

/**
 * This data class defines a quote which includes a quote, author and html string
 */
data class Quote (
    @SerializedName("q") val quote: String,
    @SerializedName("a") val author: String,
    @SerializedName("h") val htmlQuote: String
)