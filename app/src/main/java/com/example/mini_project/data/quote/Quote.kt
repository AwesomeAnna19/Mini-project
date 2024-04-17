package com.example.mini_project.data.quote

import kotlinx.serialization.Serializable


/**
 * This data class defines a quote which includes ...
 */


@Serializable
data class Quote (

    /**Quote text
     */
    val q: String,

    /**Author name
     */
    val a: String,

    /** Pre-formatted HTML quote
     */
    val h: String
)