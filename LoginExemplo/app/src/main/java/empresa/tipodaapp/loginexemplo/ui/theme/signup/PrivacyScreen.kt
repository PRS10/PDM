package empresa.tipodaapp.loginexemplo.ui.theme.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.simulateHotReload
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import empresa.tipodaapp.loginexemplo.ui.theme.LoginExemploTheme

@Composable

fun PrivacyScreen(
    onBtnClick: () -> Unit
){
    
    Box(
        modifier = Modifier.fillMaxSize(),

        ){
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Privacy Screen")
            Button(onClick = onBtnClick) {
                Text(text = "Finish")
            }
        }
    }
}

@Preview (showSystemUi = true, showBackground = true)
@Composable
fun PrevPrivacyScreen(){
    LoginExemploTheme {
        PrivacyScreen{}
    }
}