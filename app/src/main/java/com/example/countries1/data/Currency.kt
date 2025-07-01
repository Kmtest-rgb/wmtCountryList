package com.example.countries1.data

import kotlinx.serialization.Serializable

@Serializable
data class Currency (
    val code:String = " ",
    val name: String = " ",
    val symbol: String = " ",
)

