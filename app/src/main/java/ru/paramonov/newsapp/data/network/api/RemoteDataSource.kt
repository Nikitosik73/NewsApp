package ru.paramonov.newsapp.data.network.api

import ru.paramonov.newsapp.data.network.model.NewsResponseDto

interface RemoteDataSource {

    suspend fun fetchNews(): NewsResponseDto
}