package empresa.tipodaapp.loginexemplo.ui.theme.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


// Para mostra como fazer navegação
@Composable
fun PolicyScreen(
    onBtnClick: () -> Unit
){

    Box(modifier = Modifier.fillMaxSize()){
        Column {
            Text(text = "Policy Screen")
            Button(onClick = onBtnClick) {
                Text(text = "Finish")
            }
        }
    }
}