package ru.paramonov.newsapp.presentation.application

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule
import ru.paramonov.newsapp.di.module.dataModule
import ru.paramonov.newsapp.di.module.ktorModule

class NewsApplication : Application(), DIAware {

    override val di: DI by DI.lazy {
        import(androidXModule(app = this@NewsApplication))
        importAll(ktorModule, dataModule)
    }
}

@Composable
fun getKodeinDI(): DI {
    return (LocalContext.current.applicationContext as NewsApplication).di
}

