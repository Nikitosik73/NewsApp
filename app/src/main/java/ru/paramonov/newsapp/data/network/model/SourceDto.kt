package ru.paramonov.newsapp.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SourceDto(
    val id: String? = null,
    val name: String
)