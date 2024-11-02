package ipca.aulas.productsapp.data.product

// Trata da resposta da API
sealed class Results <T>(
    val data: T? = null,
    val message: String? = null,
//    val isLoading: Boolean? = false
){
    class Success<T> (data: T? = null): Results<T>(data)
    class Error<T> (data: T? = null, message: String?): Results<T>(data, message)
//    class Loading<T> (isLoading: Boolean?): Results<T>()
}