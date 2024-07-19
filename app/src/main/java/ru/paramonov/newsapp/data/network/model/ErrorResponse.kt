package ru.paramonov.newsapp.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String
)