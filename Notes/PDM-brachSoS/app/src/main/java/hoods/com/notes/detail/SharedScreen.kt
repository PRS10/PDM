package hoods.com.notes.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import hoods.com.notes.components.SearchTextField

@Composable
fun SharedScreenPopUp(
    isPopupVisible: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onQueryChange: () -> Unit = {}
    )

    {
        if (isPopupVisible.value) {
            // Fundo escurecido por trás do Popup
            Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 0.6f)) // Fundo escuro e transparente
                        .clickable { isPopupVisible.value = false } // Fecha o Popup ao clicar fora
            )

            // Popup no centro da tela
            Popup(
                    alignment = Alignment.Center,
                    onDismissRequest = { isPopupVisible.value = false },
                    properties = PopupProperties(
                            focusable = true
                    )
            ) {
                Box(
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.9f))
                            .width(380.dp)
                            .height(550.dp)
                            .shadow(
                                    elevation = 10.dp, // Intensidade da sombra
                                    shape = RoundedCornerShape(16.dp), // Sombra com cantos arredondados
                                    ambientColor = Color.Black.copy(alpha = 0.3f), // Cor da sombra difusa
                                    spotColor = Color.Black.copy(alpha = 0.5f) // Cor da sombra projetada
                            )
                )
                {

                    SharedScreen(
                            modifier = modifier,
                            queryChange = {onQueryChange()}
                            )

                }

            }
        }

}

@Composable
fun SharedScreen(
    modifier: Modifier = Modifier,
    queryChange: () -> Unit = {}

){

    Box(
            modifier = modifier.fillMaxSize().padding(30.dp),

    ){
        Column (
            modifier = modifier.fillMaxSize()
        ) {
            SearchTextField(
                query = "Search",
                onQueryChange = {
                    queryChange()
                }
            )
        }
    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(showSystemUi = true, showBackground = true)

fun SharedScrennPreview(){
    SharedScreenPopUp(isPopupVisible = mutableStateOf(true))

}
//@SuppressLint("UnrememberedMutableState")
//@Composable
//@Preview(showSystemUi = true, showBackground = true)
//fun SharedScreenPrev(){
//    SharedScreen()
//
//}