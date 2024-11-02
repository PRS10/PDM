package ipca.aulas.productsapp.presentation // Ajuste o pacote conforme necessário

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import ipca.aulas.productsapp.screens.ListaProdutos.ProductRow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel,
    modifier: Modifier = Modifier,
    onProductClick: (String) -> Unit
) {
    val productsList = viewModel.productX.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
        viewModel.showErrorToastChannel.collectLatest { show ->
            if (show) {
                Toast.makeText(context, "Erro ao carregar produtos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (productsList.isEmpty()) {
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .padding(top = 30.dp)
            , // Usa o modificador recebido
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {
            items(productsList.size) { index ->
                ProductRow(productsList[index],
                    onClick = {
                    onProductClick(productsList[index].id.toString())
                })
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}