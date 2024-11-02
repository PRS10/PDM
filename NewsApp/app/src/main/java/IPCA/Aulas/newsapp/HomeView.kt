package IPCA.Aulas.newsapp

import IPCA.Aulas.newsapp.models.Article
import IPCA.Aulas.newsapp.ui.theme.NewsAppTheme
import android.os.Build
import android.view.PixelCopy.Request
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.IOException
import org.json.JSONObject


@Composable
fun Home(modifier: Modifier = Modifier) {
    val viewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState()


    val paraExemplo = arrayListOf(
        Article("Title 1", "Description 1", "url 1",
            "urlToImage 1", "publishedAt 1",
            "content 1"),
        Article("Title 2", "Description 2", "url 2",
            "urlToImage 2", "publishedAt 2",
            "content 2"),
        Article("Title 3", "Description 3", "url 3",
            "urlToImage 3", "publishedAt 3",
            "content 3"),
    )


    LazyColumn ( // Faz a paginação automaticamente!
        modifier = modifier.fillMaxSize()
    ) {
        itemsIndexed(

            items = uiState.value.articles
        ){
            _, item -> // Se não estivermos a utilizar a varivel podemos por _
            ArticleRowView(article = item)
        }
    }

    LaunchedEffect(key1 = true) { // garante que só corre uma vez

//        GlobalScope.launch(Dispatchers.IO) { // isto é apenas para saltar a thread principal
//
//
//        val client = OkHttpClient()
//
//        val request = okhttp3.Request.Builder()
//            .url("https://newsapi.org/v2/everything?q=tesla&from=2024-09-21&sortBy=publishedAt&apiKey=fd729629d4724c6a9a17a41647f18b65")
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                response.use {
//                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
//
//                    val result = response.body!!.string()
//                    val jsonObject = JSONObject(result)
//                    val status = jsonObject.getString("status")
//
//                    if (status == "ok") {
//                        val articlesArray = jsonObject.getJSONArray("articles")
//                        for (i in 0 until articlesArray.length()) {
//
//                            val article = Article.fromJson(articlesArray.getJSONObject(i))
//                            articles.add(article)
//                        }
//                    }
//
//                }
//            }
//        })
//        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
    NewsAppTheme {
        Home()
    }
}