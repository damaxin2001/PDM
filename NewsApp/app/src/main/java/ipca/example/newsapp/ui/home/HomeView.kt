package ipca.example.newsapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import ipca.example.newsapp.models.Article
import ipca.example.newsapp.models.encodeURL
import ipca.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun HomeView( modifier: Modifier = Modifier ,
              onArticleClick: (String) -> Unit = {},
              searchQuery: String = "") {

    val viewModel : HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val filteredArticles = if(searchQuery.isEmpty()){
        uiState.articles
    }else{
        uiState.articles.filter { article ->
            article.title?.contains(searchQuery, ignoreCase = true) == true
        }
    }
    HomeViewContent(
        modifier = Modifier,
        uiState = uiState.copy(articles = filteredArticles),
        onArticleClick = onArticleClick
    )

    LaunchedEffect(key1 = true) {
        viewModel.fetchArticles()
    }

}

@Composable
fun HomeViewContent(
    modifier: Modifier = Modifier,
    uiState: ArticleState,
    onArticleClick: (String) -> Unit = {}) {
    if (uiState.isLoading) {
        Box(modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center)
        {
            Text(text = "Loading...")
        }
    }else if(uiState.errorMessage.isNotEmpty()){
        Box(modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center)
        {
            Text(text = uiState.errorMessage)
        }
    }else{
        LazyColumn(modifier = modifier.fillMaxSize()) {
            itemsIndexed(
                items = uiState.articles
            ){
                    _, item ->
                ArticleRowView(modifier = Modifier
                    .clickable {
                        onArticleClick(item.toJsonString())
                    },
                    article = item)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    NewsAppTheme {
        HomeViewContent(
            uiState = ArticleState(
                articles = arrayListOf(
                    Article(
                        title = "Title 1",
                        description = "Description 1",
                        url = "https://www.google.com",
                        urlToImage = null,
                        tipo = "tipo 1",
                        publishedAt = null
                    ),
                    Article(
                        title = "Title 2",
                        description = "Description 2",
                        url = "https://www.google.com",
                        urlToImage = null,
                        tipo = "tipo 2",
                        publishedAt = null
                    ),
                )
            )
        )
    }
}
