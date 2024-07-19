package ru.paramonov.newsapp.di.module

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import ru.paramonov.newsapp.data.network.api.RemoteDataSource
import ru.paramonov.newsapp.data.network.api.RemoteResponseImpl

val dataModule = DI.Module(
    name = "DataModule",
    init = {
        bind<RemoteDataSource>() with singleton {
            RemoteResponseImpl(client = instance())
        }
    }
)