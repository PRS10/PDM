package ipca.aulas.productsapp.screens.produto

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import ipca.aulas.productsapp.data.product.model.ProductX

@Composable
fun ProductDetailScreen(product: ProductX) {
    // Ver se faz sentido passar por parametros.
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(product.thumbnail)
            .size(Size.ORIGINAL)
            .build()
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Verifica o estado da imagem para exibir carregando ou erro
        when (imageState.state) {
            is AsyncImagePainter.State.Error -> {
                Text(text = "Erro ao carregar imagem", color = Color.Red)
            }
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator()

            }
            is AsyncImagePainter.State.Success -> {
                Image(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    painter = imageState,
                    contentDescription = product.title,
                    contentScale = ContentScale.Crop
                )
            }

            AsyncImagePainter.State.Empty -> TODO()
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Exibir título do produto
        Text(
            text = product.title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )

        // Exibir preço do produto
        Text(
            text = "Price: ${product.price} €",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Exibir status de disponibilidade
        Text(
            text = product.availabilityStatus,
            style = MaterialTheme.typography.bodyMedium,
            color = if (product.availabilityStatus == "In Stock") Color.Green else Color.Red
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Exibir descrição do produto
        Text(
            text = product.description,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
            maxLines = 4, // Limitar o número de linhas
            overflow = TextOverflow.Ellipsis // Adicionar "..." quando ultrapassar
        )
    }
}