import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import empresa.tipodaapp.loginexemplo.MyNavigation
import empresa.tipodaapp.loginexemplo.ui.theme.LoginExemploTheme
import empresa.tipodaapp.loginexemplo.ui.theme.login.GoogleAuthUiClient

class MainActivity : ComponentActivity() {

    private lateinit var googleAuthUiClient: GoogleAuthUiClient
    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        googleAuthUiClient = GoogleAuthUiClient(this)
        viewModel = ViewModelProvider(this, SignInViewModelFactory(googleAuthUiClient))
            .get(SignInViewModel::class.java)

        setContent {
            LoginExemploTheme(dynamicColor = false) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                ) { innerPadding ->
                    val navController = rememberNavController()
                    MyNavigation(navHostController = navController)
                }
            }
        }
    }
}