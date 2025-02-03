package ipca.example.newsapp.ui.home

import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ipca.example.newsapp.models.Article
import ipca.example.newsapp.respository.ArticlesAPI
import ipca.example.newsapp.respository.ArticlesRepository
import ipca.example.newsapp.respository.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

data class ArticleState  (
    val articles : List<Article> = arrayListOf<Article>(),
    val isLoading  : Boolean = false,
    val errorMessage: String = "",
    val searchQuery: String = ""
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    val articlesRepository: ArticlesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArticleState())
    val uiState: StateFlow<ArticleState> = _uiState.asStateFlow()

    fun fetchArticles() {
        articlesRepository
            .fetchArticles("")
            .onEach { result ->
                when(result){
                    is ResultWrapper.Success ->{
                        _uiState.value = ArticleState(
                            articles = result.data?: emptyList(),
                            isLoading = false
                        )
                    }
                    is ResultWrapper.Loading ->{
                        _uiState.value = ArticleState(
                            isLoading = true
                        )
                    }
                    is ResultWrapper.Error ->{
                        _uiState.value = ArticleState(
                            isLoading = false,
                            errorMessage = result.message?:""
                        )
                    }
                }

            }.launchIn(viewModelScope)

    }
}