import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import empresa.tipodaapp.loginexemplo.ui.theme.login.GoogleAuthUiClient
import empresa.tipodaapp.loginexemplo.ui.theme.login.SignInResult
import empresa.tipodaapp.loginexemplo.ui.theme.login.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val googleAuthUiClient: GoogleAuthUiClient // injetado no ViewModel
) : ViewModel() {

    // Estado interno e público
    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state

    // Função para iniciar o login com Google
    fun initiateGoogleSignIn() = viewModelScope.launch {
        try {
            val intentSender = googleAuthUiClient.signIn()
            _state.update { it.copy(intentSender = intentSender) }
        } catch (e: Exception) {
            _state.update { it.copy(signInError = e.message) }
        }
    }

    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage,
                intentSender = null // Limpar o intentSender após o uso
            )
        }
    }
}