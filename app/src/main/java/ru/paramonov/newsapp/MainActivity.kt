package ru.paramonov.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance
import ru.paramonov.newsapp.data.network.api.RemoteDataSource
import ru.paramonov.newsapp.data.network.api.RemoteResponseImpl
import ru.paramonov.newsapp.data.network.model.ArticleDto
import ru.paramonov.newsapp.data.network.model.NewsResponseDto
import ru.paramonov.newsapp.presentation.application.getKodeinDI
import ru.paramonov.newsapp.presentation.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val di = getKodeinDI()
            val remoteDataSource: RemoteDataSource by di.instance()

            val newsListState = remember {
                mutableStateOf<List<ArticleDto>>(value = emptyList())
            }
            LaunchedEffect(key1 = Unit) {
                val newsList = remoteDataSource.fetchNews()
                newsListState.value = newsList.articles
            }

            NewsAppTheme {
                Scaffold {
                    NewsList(articles = newsListState.value, paddingValues = it)
                }
            }
        }
    }
}

@Composable
fun NewsList(
    articles: List<ArticleDto>,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
    ) {
        items(
            items = articles
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                it.author?.let { author ->
                    Text(text = author, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(4.dp))

                it.description?.let { description ->
                    Text(text = description, fontSize = 14.sp)
                }
            }
        }
    }
}