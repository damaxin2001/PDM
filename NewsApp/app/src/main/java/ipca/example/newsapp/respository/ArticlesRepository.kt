package ipca.example.newsapp.respository

import android.content.Context

import ipca.example.newsapp.AppDatabase
import ipca.example.newsapp.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val articlesApi : ArticlesAPI,
    private val context: Context
){

    fun fetchArticles(path: String) : Flow<ResultWrapper<List<Article>>> =
        flow{
            emit(ResultWrapper.Loading())
            try {
                val articles = articlesApi.fetchArticles(path)
                emit(ResultWrapper.Success(articles))
            }catch (e : IOException){
                emit(ResultWrapper.Error(e.localizedMessage?:"unknown error"))
            }

    }.flowOn(Dispatchers.IO)

    fun fetchArticlesFromDb() : Flow<ResultWrapper<List<Article>>> =
        flow{
            emit(ResultWrapper.Loading())
            val articlesCache = AppDatabase
                .getDatabase(context)
                ?.articleCacheDao()
                ?.getAll()

            val articles : ArrayList<Article> = arrayListOf()
            for (a in articlesCache?: emptyList() ){
                articles.add(Article.fromJson(JSONObject(a.articleJsonString)))
            }
            emit(ResultWrapper.Success(articles.toList()))

    }.flowOn(Dispatchers.IO)

    /*
    fun fetchArticlesFromDb(url: String) : Flow<ResultWrapper<Article>> =
        flow{
            emit(ResultWrapper.Loading())
            val articleCache = AppDatabase
                .getDatabase(context)
                ?.articleCacheDao()
                ?.getByUrl(url)

            articleCache?.let {

                val article : Article =Article.fromJson(JSONObject(it.articleJsonString))
                emit(ResultWrapper.Success(article))
            }?:run {
                emit(ResultWrapper.Error("article does not exist"))
            }

        }.flowOn(Dispatchers.IO)
*/
}