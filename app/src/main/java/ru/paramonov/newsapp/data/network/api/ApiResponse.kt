package ru.paramonov.newsapp.data.network.api

sealed interface ApiResponse<T, E> {

    data class Success<T>(val data: T): ApiResponse<T, Nothing>

    data class Error<E>(
        val errorCode: Int,
        val message: String,
        val errorData: E? = null
    ): ApiResponse<Nothing, E>
}