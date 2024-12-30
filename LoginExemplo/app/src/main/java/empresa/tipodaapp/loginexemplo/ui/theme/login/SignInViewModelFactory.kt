import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import empresa.tipodaapp.loginexemplo.ui.theme.login.GoogleAuthUiClient

// Para criar instancias do viewModel
class SignInViewModelFactory(
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignInViewModel(googleAuthUiClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}