package ipca.aulas.retrofitteste

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ipca.aulas.retrofitteste.dados.Api
import ipca.aulas.retrofitteste.dados.models.ultimasnoticias.News
import ipca.aulas.retrofitteste.dados.models.ultimasnoticias.Noticia
import ipca.aulas.retrofitteste.ui.theme.RetrofitTesteTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
private val BASE_URL = "https://www.publico.pt/api/list/ultimas/"
private val TAG: String = "CHECK_RESPONSE"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetrofitTesteTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column (
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(text = "ok")
                    }
                    getAllNews()
                }
            }
        }
    }


    private fun getAllNews(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)

        api.getUltimas().enqueue(object : Callback<List<Noticia>> { // Chamar o API
            override fun onResponse(call: Call<List<Noticia>>, response: Response<List<Noticia>>) {
                response.body()?.let {
                    for(news in it){
                        Log.i(TAG, "onResponse: ${news.indices}")
                    }
                }
            }

            override fun onFailure(call: Call<List<Noticia>>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }


        })
    }



}
