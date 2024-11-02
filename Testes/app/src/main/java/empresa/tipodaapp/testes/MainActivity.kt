package empresa.tipodaapp.testes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import empresa.tipodaapp.testes.ui.theme.TestesTheme

import android.content.Context
import android.os.BatteryManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScreenContent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ScreenContent( modifier: Modifier = Modifier){
    var name: String by remember {
        mutableStateOf("")
    }

    val contexto = LocalContext.current

    val batery by remember {
        mutableStateOf(GetBatteryTemperature(contexto))
    }

    Column {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Insira aqui o seu texto!") },

            )
        Text(
            text = "${batery}",
        )

    }
}

fun GetBatteryTemperature(context: Context): Float {
    val intentFilter = android.content.IntentFilter(android.content.Intent.ACTION_BATTERY_CHANGED)
    val batteryStatus = context.registerReceiver(null, intentFilter)
    val temperature = batteryStatus?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) ?: 0
    return temperature / 10f // Valor retornado está em décimos de graus Celsius, por isso é dividido por 10
}

@Preview
@Composable
fun Teste(){

    Text(
        text = stringResource(id = R.string.ok),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.error)


        )


}