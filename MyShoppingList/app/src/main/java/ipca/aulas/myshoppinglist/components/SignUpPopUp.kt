package ipca.aulas.myshoppinglist.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun AutoOpenPopupExample(onClosePopup: () -> Unit) {
    val isPopupVisible = remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Popup com fundo transparente
        if (isPopupVisible.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // Fundo escurecido
                    .clickable {
                        isPopupVisible.value = false
                        onClosePopup() // Chama a função para fechar a navegação
                    }
            )

            // Popup Window
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = {
                    isPopupVisible.value = false
                    onClosePopup() // Chama a função para fechar a navegação
                },
                properties = PopupProperties(
                    focusable = true
                )
            ) {
                Box(
                    modifier = Modifier
                        .size(250.dp)
                        .background(Color.White.copy(alpha = 0.9f))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Este é o Popup!",
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Button(onClick = {
                            isPopupVisible.value = false
                            onClosePopup()
                        }) {
                            Text(text = "Fechar")
                        }
                    }
                }
            }
        }
    }
}