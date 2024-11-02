package IPCA.Aulas.newsapp

import IPCA.Aulas.newsapp.models.Article
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

data class ArticleState ( // representa o estado deste ecra
    val articles: ArrayList<Article> = arrayListOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class HomeViewModel{

    private val _uistate = MutableStateFlow(ArticleState()) // Utilizar para guardar o estado (definidos em cima)

    fun fetchArticles() {
        _uistate.value = ArticleState(isLoading = true)

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("http://publicobject.com/helloworld.txt")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                _uistate.value = ArticleState(isLoading = false, errorMessage = e.message)

            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val articlesResult = arrayListOf<Article>()
                    val result = response.body!!.string()

                    val jsonObject = JSONObject(result)
                    val status = jsonObject.getString("status")

                    if (status == "ok") {
                        val articlesArray = jsonObject.getJSONArray("articles")
                        for (i in 0 until articlesArray.length()) {
                            val articlesObject = articlesArray.getJSONObject(i)
                            val article = Article.fromJson(articlesArray.getJSONObject(i))
                            articlesResult.add(article)
                        }
                        _uistate.value = ArticleState(
                            articles = articlesResult,
                            isLoading = false,
                            errorMessage = null // Não era necessario
                        )
                    }


                }
            }
        })


    }
}