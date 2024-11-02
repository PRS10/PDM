package empresa.tipodaapp.calculadorahome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import empresa.tipodaapp.calculadorahome.ui.theme.CalculadoraHomeTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraHomeTheme {
                val viewModel:CalculatorViewModel = viewModel()
                val state = viewModel.state
                val espacoBotao = 8.dp

                Calculadora(
                    state = state,
                    espacamentoBotao = espacoBotao,
                    onAction = viewModel::onAction,
                    modifier = Modifier.fillMaxSize()
                        .background(MediumGray)
                        .padding(16.dp)
                )

                }
            }
        }
    }


