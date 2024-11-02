package empresa.tipoDaApp.helloworld

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import kotlin.Int
import kotlin.Int as Int1

@Composable
fun Botao(conteudo:String, contador: Int, onIncrement : () -> Unit){
    Button(onClick = {
        onIncrement()
    }) {
        Text(text = "Contador: ${contador}")
    }
}