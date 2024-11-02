package ipca.aulas.productsapp.data.product

import ipca.aulas.productsapp.data.product.model.ProductX
import kotlinx.coroutines.flow.Flow

// A interface para os produtos
interface ProductsRep {
    suspend fun getProductsList(): Flow<Results<List<ProductX>>> // Flow é uma sequencia de coisas que podem acontecer
}