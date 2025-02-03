package ipca.example.newsapp.models

import android.util.Log
import org.json.JSONObject
import java.net.URLDecoder
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date
import android.text.Html
import android.os.Build
import ipca.example.newsapp.R

fun String.cleanHtml(): String {
   return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString().trim()
}

fun String.encodeURL() : String{
    return  URLEncoder.encode(this, "UTF-8")
}

fun String.decodeURL() : String{
    return  URLDecoder.decode(this, "UTF-8")
}

fun String.toDate(): Date {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
    return dateFormat.parse(this)
}

fun Date.toStringDate(): String {
    val dateFormat = SimpleDateFormat("yyyy/MM/dd")
    return dateFormat.format(this)
}

fun Date.toServerStringDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    return dateFormat.format(this)
}

data class Article (
    var title           : String?,
    var description     : String?,
    var url             : String?,
    var urlToImage      : String?,
    var tipo            : String?,
    var publishedAt     : Date?
){
    companion object{

        fun fromJson(articleObject: JSONObject): Article {
            val title       = articleObject.getString("titulo")
            val description = articleObject.optString("descricao", "").takeIf { it != "null" }?.cleanHtml() ?: ""
            val url         = articleObject.getString("url")
            val urlToImage  = articleObject.getString("multimediaPrincipal")
            val tipo       = articleObject.optString("tipo", "")
            val publishedAt = articleObject.getString("data" ).toDate()
            return Article(title, description, url, urlToImage, tipo, publishedAt)
        }
    }

    fun toJsonString() : String{
        val jsonObject = JSONObject()
        jsonObject.put("titulo"      , title      )
        jsonObject.put("descricao", description)
        jsonObject.put("url"        , url?.encodeURL()         )
        jsonObject.put("multimediaPrincipal" , urlToImage?.encodeURL()  )
        jsonObject.put("tipo"      , tipo      )
        jsonObject.put("data", publishedAt?.toServerStringDate()  )
        return jsonObject.toString()
    }
}