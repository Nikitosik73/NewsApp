package ru.paramonov.newsapp.di.module

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import ru.paramonov.newsapp.BuildConfig

val ktorModule = DI.Module(
    name = "KtorModule",
    init = {
        bind<Json>() with singleton {
            Json {
                isLenient = true
                ignoreUnknownKeys = true
            }
        }

        bind<HttpClient>() with singleton {
            val json: Json = instance()

            HttpClient {
                install(Logging) {
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json(json = json)
                }
                defaultRequest {
                    url {
                        header(key = "Authorization", value = "Bearer ${BuildConfig.NEWS_API_KEY}")
                    }
                }
            }
        }
    }
)