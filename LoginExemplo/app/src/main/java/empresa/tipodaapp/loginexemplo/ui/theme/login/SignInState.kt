package empresa.tipodaapp.loginexemplo.ui.theme.login

import android.content.IntentSender

data class SignInState(
    val intentSender: IntentSender? = null,
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
