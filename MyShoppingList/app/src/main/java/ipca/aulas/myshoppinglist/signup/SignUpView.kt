package ipca.aulas.myshoppinglist.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ipca.aulas.myshoppinglist.components.LoginTextField
import ipca.aulas.myshoppinglist.ui.theme.LaranjaMaisMaos

@Composable
fun SignUpView(
    modifier: Modifier = Modifier,
    onSignUpSuccess: () -> Unit = {},
) {
    val viewModel: SignUpViewModel = viewModel()
    val state = viewModel.state

    Box(
        modifier.padding(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                fontSize = 60.sp,
                text = ""
            )

            LoginTextField(
                modifier = modifier,
                value = state.value.username,
                onValueChange = viewModel::onUsernameChange,
                labelText = "Email",
                leadingIcon = Icons.Default.Email
            )
            Spacer(modifier = Modifier.height(16.dp))

            LoginTextField(
                modifier = modifier,
                value = state.value.password,
                onValueChange = viewModel::onPasswordChange,
                labelText = "Password",
                leadingIcon = Icons.Default.Lock
            )
            Spacer(modifier = Modifier.height(16.dp))

            LoginTextField(
                modifier = modifier,
                value = state.value.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                labelText = "Confirm Password",
                leadingIcon = Icons.Default.Lock
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.signUp(onSignUpSuccess = onSignUpSuccess)
            },                     modifier = Modifier.width(200.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LaranjaMaisMaos, // Cor de fundo
                    contentColor = Color.Black,   // Cor do texto
                )
            ) {
                Text(
                    fontSize = 20.sp,
                    text = "Inscreva-se")
            }
        }
    }

}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PrevSignUpView() {
    SignUpView()

}