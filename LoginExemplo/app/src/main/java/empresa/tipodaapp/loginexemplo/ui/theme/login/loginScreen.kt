package empresa.tipodaapp.loginexemplo.ui.theme.login

import SignInViewModel
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import empresa.tipodaapp.loginexemplo.R
import empresa.tipodaapp.loginexemplo.ui.theme.LoginExemploTheme
import empresa.tipodaapp.loginexemplo.ui.theme.components.HeaderText
import empresa.tipodaapp.loginexemplo.ui.theme.components.LoginTextField


val defaultPadding = 16.dp
val itemSpacing = 8.dp
@Composable
fun LoginScreen(
    state: SignInState,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
){
    val (userName, setUsername) = rememberSaveable { // Guarda durante alterações na composição, Por exemplo se a orientação mudar...
        mutableStateOf("")
    } // Assim fica mais parecido com react.
    var user by remember {// esta foi a forma que aprendemos na aula. A diferença entre os dois é que
            // a forma como guardam os estados. se quisermos que fiquem permanentes utilizamos o rememberSaveable
            // Para coisas que não é necessario, utilizamos o remember
        mutableStateOf("")
    }

    val (password, setPassword) = rememberSaveable { // Guarda durante alterações na composição, Por exemplo se a orientação mudar...
        mutableStateOf("")
    }
    val (checked, setChecked) = rememberSaveable { // Guarda durante alterações na composição, Por exemplo se a orientação mudar...
        mutableStateOf(false)
    }

    val headerMesage = stringResource(R.string.Header) // Antes era getString, mas agora mudou...

    val isFielsEmpty = userName.isNotEmpty() && password.isNotEmpty()
    val context = LocalContext.current

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let{erro ->
            Toast.makeText(
                context,
                erro,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(defaultPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderText(text = headerMesage,
            modifier = Modifier
                .padding(vertical = defaultPadding)
                .align(alignment = Alignment.Start) // Apenas para o cabeçalho
        )

        LoginTextField(
            value =userName, // user,
            onValueChange = setUsername, // {new -> user = new},
            labelText = "Username",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(Modifier.height(itemSpacing))
        LoginTextField(
            value = password, // user,
            onValueChange = setPassword, // {new -> user = new},
            labelText = "Password",
            leadingIcon = Icons.Default.Lock,
            modifier = Modifier
                .fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()

        )
        Spacer(Modifier.height(itemSpacing))
        Row( // linha para lembrar e esqueceu a password
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = checked, onCheckedChange = setChecked)
                Text(text = "Lembrar")
            }
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Esqueci-me da password")
            }
        }
        Spacer(Modifier.height(itemSpacing))
        Button(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = isFielsEmpty
        ) {
            Text(text = "Login")
        }
        ALternativeLoginOptions(
            onIconClick = {index ->
                when(index){
                    0 -> {
                        Toast.makeText(context, "Instagram Click", Toast.LENGTH_SHORT).show()
                    }
                    1 -> {
                        Toast.makeText(context, "Github Click", Toast.LENGTH_SHORT).show()

                    }
                    2 -> {
                        Toast.makeText(context, "Google Click", Toast.LENGTH_SHORT).show()
                        
                    }
                }

            },
            onSignUpClick = onSignUpClick,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomCenter)
        )
    }
}

@Composable
fun ALternativeLoginOptions(
    onIconClick:(index:Int) -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
){

    val iconList = listOf(
        R.drawable.icon_instagram,
        R.drawable.icon_github,
        R.drawable.icon_google
    )
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Or Sign in With")
        Row (
            horizontalArrangement = Arrangement.Center,
        ){
            iconList.forEachIndexed{index, i ->
                Icon(painter = painterResource(id = i),
                    contentDescription = "Alternativa de login",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            onIconClick(index)
                        }
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(defaultPadding))

            }

        }
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don't have an account?")
            Spacer(modifier = Modifier.height(itemSpacing))
            TextButton(onClick = onSignUpClick) {
                Text(text = "SignUp!")
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrevLoginScreen(){
    LoginExemploTheme {
//        LoginScreen(
//            state = SignInState(),onLoginClick = {}
//        ) {
//
//        }
    }
}
