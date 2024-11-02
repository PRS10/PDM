package ipca.aulas.productsapp.data.product

import coil.network.HttpException
import ipca.aulas.productsapp.data.product.model.ProductX
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

// A implementação da interface dos produtos
class ProductsRepImplementation(
    private val api: ApiProduct
) : ProductsRep {
    override suspend fun getProductsList(): Flow<Results<List<ProductX>>> {
        return flow {
            try {
//                emit(Results.Loading(isLoading = true)) // emite flow que est
                val productsFromApi = api.getProductsList()
                emit(Results.Success(productsFromApi.products))
//                emit(Results.Loading(isLoading = false)) // emite flow que está

            } catch (e: IOException) { // Error handling
                e.printStackTrace()
                emit(Results.Error(message = "Erro ao carregar os produtosIO"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Results.Error(message = "Erro ao carregar os produtosHTTP"))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Results.Error(message = "Erro ao carregar os produtosOutros"))
            }
        }
    }
}
