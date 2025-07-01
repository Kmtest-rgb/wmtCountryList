package com.example.countries1.data
import kotlinx.serialization.Serializable

@Serializable
data class CountryDetails (
    val capital: String = " ",
    val code: String = " ",
    val currency: Currency? = null,
    val flag: String = " ",
    val language: language? = null,
    val name: String = " ",
    val region: String = ""
)



