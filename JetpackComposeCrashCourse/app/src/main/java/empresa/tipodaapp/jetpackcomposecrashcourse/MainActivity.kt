package empresa.tipodaapp.jetpackcomposecrashcourse

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import empresa.tipodaapp.jetpackcomposecrashcourse.ui.theme.JetpackComposeCrashCourseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeCrashCourseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Teste(
                        Modifier.padding(innerPadding)
                            .background(Color.Green)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun Teste(modifier: Modifier = Modifier){
    Text(
        text = "Olá!",
        modifier = modifier
            .fillMaxSize()
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    JetpackComposeCrashCourseTheme {
        Teste()
    }
}
