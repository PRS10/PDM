package empresa.tipodaapp.loginexemplo.ui.theme.login

data class SignInResult(
    val data: DadosUser?,
    val errorMessage: String?

)

data class DadosUser(
    val userId: String,
    val username: String?,
    val profilePicUrl: String?
)
