package ru.paramonov.newsapp.data.network.api.ktor

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.host
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.util.StringValues
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okio.IOException
import ru.paramonov.newsapp.data.network.api.ApiResponse
import java.net.UnknownHostException

suspend inline fun <reified T, reified E> HttpClient.safeRequest(
    host: String,
    path: String,
    params: StringValues = StringValues.Empty,
    method: HttpMethod = HttpMethod.Get,
    body: Any? = null,
    json: Json = Json
) = try {
    val response = request {
        this.method = method
        this.host = host
        url {
            protocol = URLProtocol.HTTPS
            path(path)
            parameters.appendAll(params)
        }
        contentType(type = ContentType.Application.Json)
        body?.let { body -> setBody(body = body) }
    }
    val stringBody = response.body<String>()
    Log.i("ktor-client", stringBody)
    if (response.status.isSuccess()) {
        ApiResponse.Success(data = json.decodeFromString<T>(string = stringBody))
    } else {
        ApiResponse.Error(
            errorCode = response.status.value,
            message = response.status.description,
            errorData = json.decodeFromString<E>(string = stringBody)
        )
    }
} catch (e: SerializationException) {
    e.printStackTrace()
    ApiResponse.Error(errorCode = -1, message = "Serialization exception")
} catch (e: ServerResponseException) {
    e.printStackTrace()
    ApiResponse.Error(errorCode = 500, message = "Server error")
} catch (e: UnknownHostException) {
    e.printStackTrace()
    ApiResponse.Error(errorCode = -1000, message = "No internet connection")
} catch (e: IOException) {
    e.printStackTrace()
    ApiResponse.Error(errorCode = -999, message = "Something went wrong")
} catch (e: Exception) {
    e.printStackTrace()
    ApiResponse.Error(errorCode = -99999, message = "Unknown error")
}