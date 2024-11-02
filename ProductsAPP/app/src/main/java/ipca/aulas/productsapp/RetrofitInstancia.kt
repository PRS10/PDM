package ipca.aulas.productsapp

import ipca.aulas.productsapp.data.product.ApiProduct
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Cria a instancia do Retrofit com o OkHttp
object RetrofitInstancia {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val api: ApiProduct = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(ApiProduct.BASE_URL)
        .client(client)
        .build()
        .create(ApiProduct::class.java)
}