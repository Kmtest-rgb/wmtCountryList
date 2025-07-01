package com.example.countries1.data

import kotlinx.serialization.Serializable

@Serializable

data class language(
    val code: String? = null,
    val name: String? = null
)

