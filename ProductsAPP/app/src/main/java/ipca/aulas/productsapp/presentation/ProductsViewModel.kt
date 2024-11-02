package ipca.aulas.productsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ipca.aulas.productsapp.data.product.ProductsRep
import ipca.aulas.productsapp.data.product.Results
import ipca.aulas.productsapp.data.product.model.ProductX
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val productsRep: ProductsRep
): ViewModel() {
    private val _products = MutableStateFlow<List<ProductX>>(emptyList())
    val productX = _products.asStateFlow() //

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init{
        viewModelScope.launch { // Coroutine
            productsRep.getProductsList().collectLatest { result ->
                when(result){
                    is Results.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is Results.Success -> {
                        result.data?.let { produts ->
                            _products.update { produts }
                        }
                    }
                }
            }
        }
    }
    // Função para buscar um produto específico pelo ID
    fun getProductById(productId: String): ProductX? {
        return _products.value.find { it.id.toString() == productId }

    }
}

