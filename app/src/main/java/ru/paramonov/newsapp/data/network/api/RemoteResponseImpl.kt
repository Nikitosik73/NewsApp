package ru.paramonov.newsapp.data.network.api

import io.ktor.client.HttpClient
import io.ktor.http.Parameters
import io.ktor.util.StringValues
import ru.paramonov.newsapp.data.network.api.ktor.safeRequest
import ru.paramonov.newsapp.data.network.model.ErrorResponse
import ru.paramonov.newsapp.data.network.model.NewsResponseDto

class RemoteResponseImpl(
    private val client: HttpClient
) : RemoteDataSource {

    override suspend fun fetchNews(): NewsResponseDto {
        val response = client.safeRequest<NewsResponseDto, ErrorResponse>(
            host = HOST,
            path = PATH,
            params = Parameters.build { appendAll(parametersForNews) }
        )
        return when(response) {
            is ApiResponse.Error -> throw Exception("Error fetching news: ${response.message}")
            is ApiResponse.Success -> response.data
        }
    }

    companion object {

        private const val HOST = "newsapi.org"
        private const val PATH = "v2/everything"

        private val parametersForNews = StringValues.build {
            append(name = "q", value = "bitcoin")
        }
    }
}