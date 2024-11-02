package ipca.aulas.productsapp.screens.ListaProdutos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import ipca.aulas.productsapp.data.product.model.ProductX

@Composable
fun ProductRow(
    product: ProductX,
    onClick: () -> Unit
){
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(
            product.thumbnail
        ).size(Size.ORIGINAL).build()).state
    Column (
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .height(200.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {

        if (imageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        if (imageState is AsyncImagePainter.State.Success) {
            Row (
                modifier = Modifier.fillMaxWidth()
                    .clickable(onClick = onClick),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {


                Image(
                    modifier = Modifier

                        .height(50.dp),
                    painter = imageState.painter,
                    contentDescription = product.title,
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "${product.title} \nPrice: ${product.price}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.description,
                    fontSize = 13.sp,
                    maxLines = 4, // Limite de linhas
                    overflow = TextOverflow.Ellipsis, // Adiciona "..." quando ultrapassa
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

    }
    }
}


@Preview (showBackground = true, showSystemUi = true)
@Composable
fun PrevProductScreen(){
    // Criar productx


}