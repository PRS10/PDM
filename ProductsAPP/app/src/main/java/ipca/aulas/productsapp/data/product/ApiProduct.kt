package ipca.aulas.productsapp.data.product

import ipca.aulas.productsapp.data.product.model.Product
import retrofit2.http.GET


interface ApiProduct {

//    @GET("products/{type}")
//    suspend fun getProductsList(
//        @Path("type") type: String,
//        @Query("id") idNum: Int,
//        @Query("api_key") apiKey: String // Se tivessemos que fazer querys
//    ): Product
//

    @GET("products")
    suspend fun getProductsList(): Product
    companion object{
        const val BASE_URL = "https://dummyjson.com/"
    }
}