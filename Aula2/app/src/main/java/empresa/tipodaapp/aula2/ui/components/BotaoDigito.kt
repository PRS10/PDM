package empresa.tipodaapp.aula2.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BotaoDigito(
    texto: String,
    onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .then(Modifier.padding(8.dp))
    ){
        Text(text = texto)
    }

}

@Preview(showBackground = true)
@Composable
fun BotaoDigitoPreview() {
    BotaoDigito(texto = "7", onClick = {})
}

