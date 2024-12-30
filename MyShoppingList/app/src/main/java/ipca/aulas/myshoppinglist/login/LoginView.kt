package ipca.aulas.myshoppinglist.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import ipca.aulas.myshoppinglist.R
import ipca.aulas.myshoppinglist.components.LoginTextField
import ipca.aulas.myshoppinglist.popup.AutoOpenPopupExample
import ipca.aulas.myshoppinglist.signup.SignUpView
import ipca.aulas.myshoppinglist.ui.theme.AzulMaisMaos
import ipca.aulas.myshoppinglist.ui.theme.LaranjaMaisMaos
import ipca.aulas.myshoppinglist.ui.theme.MyShoppingListTheme
import java.io.Console

@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {
    // Obtenha o ViewModel utilizando viewModel()
    val viewModel: LoginViewModel = viewModel()
    val state = viewModel.state
    val isPopupVisible = remember { mutableStateOf(false) } // Estado do Popup

    Box(
        modifier = modifier
            .fillMaxSize(),
//            .padding(10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        // Conteúdo da tela (formulário de login, botões, etc.)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween

        ) {


            Column(
                modifier = Modifier,
//                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth()
                        ,

                    fontSize = 40.sp,
                    color = Color.Black,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    text = ""
                )
                Spacer(modifier = Modifier.height(16.dp))

                LoginTextField(
                    modifier = modifier,
                    value = state.value.username,
                    onValueChange = viewModel::onUsernameChange,
                    labelText = "Email",
                    leadingIcon = Icons.Default.Email
                )

                Spacer(modifier = Modifier.height(16.dp))

                LoginTextField(
                    modifier = Modifier,
                    value = state.value.password,
                    onValueChange = viewModel::onPasswordChange,
                    labelText = "Password",
                    leadingIcon = Icons.Default.Lock
                )
                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = {
                        viewModel.login(onLoginSuccess = onLoginSuccess)
                    },
                    modifier = Modifier.width(200.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LaranjaMaisMaos, // Cor de fundo
                        contentColor = Color.Black,   // Cor do texto
                    )
                ) {
                    Text(
                        fontSize = 20.sp,
                        text = "Login")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = state.value.error ?: "")
                if (state.value.isLoading) {
                    CircularProgressIndicator()
                }
                Spacer(modifier = Modifier.height(36.dp))

                Image(
                    painter = painterResource(id = R.drawable.logotipo_sem_fundo),
                    contentDescription = "Imagem Local",
                    contentScale = ContentScale.Crop // Para ajustar a imagem ao tamanho
                )
            }



            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Não tens conta?")
                    TextButton(onClick = {
                        // Navegar para a tela de registo
                        onSignUpClick()
                    }) {
                        Text(text = "SignUp!")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))




            }

        }
        // Botão para abrir o Popup
        TextButton(
            modifier = Modifier.align(Alignment.BottomEnd)
                .fillMaxWidth(),

            onClick = {
            isPopupVisible.value = true // Quando clicar, mostra o Popup
        }) {
            Text(text = "(SignUp com popup)")
        }
        // Chama a função do Popup, passando o estado de visibilidade
        SignUpPop(isPopupVisible = isPopupVisible)

    }
}

@Composable
fun SignUpPop(isPopupVisible: MutableState<Boolean>) {
    if (isPopupVisible.value) {
        // Fundo escurecido por trás do Popup
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.6f)) // Fundo escuro e transparente
                .clickable { isPopupVisible.value = false } // Fecha o Popup ao clicar fora
        )

        // Popup no centro da tela
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { isPopupVisible.value = false },
            properties = PopupProperties(
                focusable = true
            )
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.9f))
                    .width(380.dp)
                    .height(550.dp)
                    .shadow(
                        elevation = 10.dp, // Intensidade da sombra
                        shape = RoundedCornerShape(16.dp), // Sombra com cantos arredondados
                        ambientColor = Color.Black.copy(alpha = 0.3f), // Cor da sombra difusa
                        spotColor = Color.Black.copy(alpha = 0.5f) // Cor da sombra projetada
                    )
            )
            {
                SignUpView()

            }

        }
    }
}


@Composable
fun ColorChangeButton(onClick: () -> Unit) {
    var isClicked by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isClicked) Color.Green else Color.Blue,
        animationSpec = tween(durationMillis = 300)
    )

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = {
            isClicked = !isClicked
            onClick()
        },
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
    ) {
        Text(text = "Login")
    }
}

@Composable
fun LoadingButton(isLoading: Boolean, onClick: () -> Unit) {
    val width by animateDpAsState(targetValue = if (isLoading) 50.dp else 250.dp)

    Button(
        modifier = Modifier
            .width(width)
            .padding(16.dp),
        onClick = {
            if (!isLoading) onClick()
        },
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
        } else {
            Text(text = "Login")
        }
    }
}

@Composable
fun AnimatedVisibilityButton(onClick: () -> Unit, texto: String = "Login") {
    var isVisible by remember { mutableStateOf(true) }

    AnimatedVisibility(visible = isVisible) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                isVisible = !isVisible
                onClick()
            }
        ) {
            Text(text = texto)
        }
    }
}

@Composable
fun AnimatedLoginButton(onClick: () -> Unit) {
    var isClicked by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isClicked) 6f else 1f,
        animationSpec = tween(durationMillis = 1000)
    )

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .scale(scale), // Aplica o efeito de escala
        onClick = {
            isClicked = true
            onClick()
            isClicked = false
        }
    ) {
        Text(text = "Login")
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginViewPreview() {
    MyShoppingListTheme {
        LoginView()
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SignUpPopUpPreview() {
    MyShoppingListTheme {
        SignUpPop(
            isPopupVisible = remember { mutableStateOf(true) }

        )
    }
}