package ru.paramonov.newsapp.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponseDto(
    val articles: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)