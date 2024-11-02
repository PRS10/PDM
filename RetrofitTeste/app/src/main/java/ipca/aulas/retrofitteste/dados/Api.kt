package ipca.aulas.retrofitteste.dados

import ipca.aulas.retrofitteste.dados.models.ultimasnoticias.News
import ipca.aulas.retrofitteste.dados.models.ultimasnoticias.Noticia
import retrofit2.http.GET
import retrofit2.Call


interface Api {
    @GET("news")
    fun getUltimas(): Call<List<Noticia>>


}