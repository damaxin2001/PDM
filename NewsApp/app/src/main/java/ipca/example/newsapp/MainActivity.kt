package ipca.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ipca.example.newsapp.models.Article
import ipca.example.newsapp.ui.ArticleDetailView
import ipca.example.newsapp.ui.bookmarks.BookmarksView
import ipca.example.newsapp.ui.components.MyBottomBar
import ipca.example.newsapp.ui.components.MyTopBar
import ipca.example.newsapp.ui.home.HomeView
import ipca.example.newsapp.ui.theme.NewsAppTheme
import org.json.JSONObject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                val navController = rememberNavController()
                var isBaseScreen by remember{ mutableStateOf(false) }
                var article by remember { mutableStateOf<Article?>(null) }
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        MyTopBar(
                            "News App",
                            isBaseScreen = isBaseScreen,
                            article = article

                        )
                    },
                    bottomBar = {
                        MyBottomBar(navController = navController

                        )
                    }
                ) { innerPadding ->

                    NavHost(navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding))  {
                        composable( route = Screen.Home.route){
                            isBaseScreen = true
                            article = null
                            HomeView(
                                modifier = Modifier.padding(innerPadding),

                                onArticleClick = {
                                    navController.navigate("article/${it}")
                                }
                            )
                        }
                        composable(route = Screen.Bookmarks.route) {
                            isBaseScreen = true
                            article = null
                            BookmarksView(){
                                navController.navigate("article/${it}")
                            }
                        }
                        composable(route = Screen.Article.route){
                            isBaseScreen = false
                            val articleJson = it.arguments?.getString("articleJson")
                            article = Article.fromJson(JSONObject(articleJson))
                            article?.let {
                                ArticleDetailView(article = it)
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Article : Screen("article/{articleJson}")
    object Bookmarks : Screen("bookmarks")
}
