package ipca.example.newsapp.respository

import ipca.example.newsapp.models.Article
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

const val API_URL = "https://www.publico.pt/api/list/ultimas"

object ArticlesAPI {

    val client = OkHttpClient()

    @Throws(IOException::class)
    suspend fun fetchArticles(path: String): List<Article>  {

        val request = Request.Builder()
            .url("$API_URL$path")
            .build()

        val resultRequest = client.newCall(request).await()

        //println("${result.code}: ${result.message}")

        if (!resultRequest.isSuccessful) throw IOException("Unexpected code ${resultRequest.networkResponse}")

        val articlesResult = arrayListOf<Article>()
        val result = resultRequest.body!!.string()

        val jsonArray = JSONArray(result)
        for (index in 0 until jsonArray.length()) {
            val articleObject = jsonArray.getJSONObject(index)
            val article = Article.fromJson(articleObject)
            articlesResult.add(article)
            println(article.urlToImage)
        }

        return articlesResult

    }

    suspend fun Call.await(recordStack: Boolean = false): Response {
        val callStack = if (recordStack) {
            IOException().apply {
                // Remove unnecessary lines from stacktrace
                // This doesn't remove await$default, but better than nothing
                stackTrace = stackTrace.copyOfRange(1, stackTrace.size)
            }
        } else {
            null
        }
        return suspendCancellableCoroutine { continuation ->
            enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    continuation.resume(response)
                }

                override fun onFailure(call: Call, e: IOException) {
                    // Don't bother with resuming the continuation if it is already cancelled.
                    if (continuation.isCancelled) return
                    callStack?.initCause(e)
                    continuation.resumeWithException(callStack ?: e)
                }
            })

            continuation.invokeOnCancellation {
                try {
                    cancel()
                } catch (ex: Throwable) {
                    //Ignore cancel exception
                }
            }
        }
    }
}