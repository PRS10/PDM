package ipca.aulas.myshoppinglist.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

data class SignUpState (
    var username: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class SignUpViewModel: ViewModel() {
    var state = mutableStateOf(SignUpState())
        private set

    fun onUsernameChange(newValue: String) {
        state.value = state.value.copy(username = newValue)
    }
    fun onPasswordChange(newValue: String) {
        state.value = state.value.copy(password = newValue)
    }
    fun onConfirmPasswordChange(newValue: String) {
        state.value = state.value.copy(confirmPassword = newValue)
    }


    fun signUp(onSignUpSuccess: () -> Unit) {
        if (validateUsername() && validatePassword() && validateConfirmPassword()) {
            state.value = state.value.copy(isLoading = true)

            val email = state.value.username
            val password = state.value.password

            // Firebase Authentication
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Registo bem-sucedido
                        state.value = state.value.copy(isLoading = false)
                        onSignUpSuccess()
                    } else {
                        // Erro no registo
                        state.value = state.value.copy(
                            isLoading = false,
                            error = task.exception?.message ?: "Erro ao criar conta"
                        )
                    }
                }
        } else {
            state.value = state.value.copy(error = "Email ou password inválidos")
        }
    }

    // Validações
    private fun validateUsername(): Boolean {
        return state.value.username.isNotEmpty()
    }
    private fun validatePassword(): Boolean {
        return state.value.password.isNotEmpty()
    }
    private fun validateConfirmPassword(): Boolean {
        return state.value.confirmPassword.isNotEmpty()
    }








}