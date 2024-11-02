package empresa.tipodaapp.loginexemplo.ui.theme.signup

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import empresa.tipodaapp.loginexemplo.R
import empresa.tipodaapp.loginexemplo.ui.theme.LoginExemploTheme
import empresa.tipodaapp.loginexemplo.ui.theme.components.HeaderText
import empresa.tipodaapp.loginexemplo.ui.theme.components.LoginTextField
import empresa.tipodaapp.loginexemplo.ui.theme.login.defaultPadding
import empresa.tipodaapp.loginexemplo.ui.theme.login.itemSpacing

@Composable

fun SignUpScreen(
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
    onPolicyClick: () -> Unit,
    onPrivacyClick: () -> Unit,
){
    val headerMesage = stringResource(R.string.SignUp)
    val (password, setPassword) = rememberSaveable { mutableStateOf("") }
    val (firstName, setFirstName) = rememberSaveable { mutableStateOf("") }
    val (lastName, setLastName) = rememberSaveable { mutableStateOf("") }
    val (email, setEmail) = rememberSaveable { mutableStateOf("") }
    val (confirmPassword, setConfirm) = rememberSaveable { mutableStateOf("") }
    val (agree, setAgree) = rememberSaveable { mutableStateOf(false) }
    var isPasswordMatch by remember {
        mutableStateOf(false)
    }
    var isFieldsNotEmpty = firstName.isNotEmpty() && lastName.isNotEmpty() &&
            email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() &&
            agree
    val context = LocalContext.current
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Existem outras formas,
            // mais eficientes, aqui funciona porque é tem informaçao
            .padding(defaultPadding),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        AnimatedVisibility(visible = isPasswordMatch) {
            Text(text = "Password is not the same", color = MaterialTheme.colorScheme.error)
        }
        HeaderText(text = headerMesage,
            modifier = Modifier
                .padding(vertical = defaultPadding)
                .align(alignment = Alignment.Start) // Apenas para o cabeçalho
        )
        Spacer(Modifier.height(itemSpacing * 5))

        LoginTextField(
            value = firstName, // user,
            onValueChange = setFirstName, // {new -> user = new},
            labelText = "First Name",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier
                .fillMaxWidth()
            )
        Spacer(Modifier.height(itemSpacing))

        LoginTextField(
            value = lastName, // user,
            onValueChange = setLastName, // {new -> user = new},
            labelText = "Last Name",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(Modifier.height(itemSpacing))
        LoginTextField(
            value = email, // user,
            onValueChange = setEmail, // {new -> user = new},
            labelText = "Email",
            leadingIcon = Icons.Default.Email,
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
        LoginTextField(
            value = confirmPassword, // user,
            onValueChange = setConfirm, // {new -> user = new},
            labelText = "Confirm Password",
            leadingIcon = Icons.Default.Lock,
            modifier = Modifier
                .fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(itemSpacing))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val privacyText = "Privacy"
            val policyText = "Policy"
            val annotatedString = buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)){
                    append("I Agree with")
                }
                append(" ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)){
                    pushStringAnnotation(tag = privacyText, privacyText)
                    append(privacyText)
                }
                append(" and ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)){
                    pushStringAnnotation(tag = policyText, policyText)
                    append(policyText)
                }
            }
            Checkbox(checked = agree, onCheckedChange =setAgree)// Tambem podemos fazer assim...
            ClickableText(
                text = annotatedString
            ) {offset ->
                annotatedString.getStringAnnotations(offset, offset).forEach{
                    when(it.tag){
                        privacyText -> {
                            Toast.makeText(context, "Privacy...", Toast.LENGTH_SHORT).show()
                            onPrivacyClick()
                        }
                        policyText -> {
                            Toast.makeText(context, "Policy clicked...", Toast.LENGTH_SHORT).show()
                            onPolicyClick()
                        }
                    }
                }
                
            }
        }
        Spacer(Modifier.height(itemSpacing * 5))
        Button(onClick = {
            isPasswordMatch = password != confirmPassword
            if(!isPasswordMatch){
                onSignUpClick()
            }
        },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFieldsNotEmpty,
            ) {
            Text(text = "Registar")
        }
        Spacer(Modifier.height(itemSpacing * 2))
        val singinTx = "Sign In"
        val singinAnnotation = buildAnnotatedString {
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)){
                append("Already have an account")
            }
            append(" ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)){
                pushStringAnnotation(singinTx, singinTx)
                append(singinTx)
            }
        }

        ClickableText(text = singinAnnotation) { offset ->
            singinAnnotation.getStringAnnotations(offset, offset).forEach{
                if (it.tag == singinTx){
                    Toast.makeText(context, "Sign in", Toast.LENGTH_SHORT).show()
                    onLoginClick()
                }
            }
        }
    }
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
fun PrevSignUp(){
    LoginExemploTheme {
        SignUpScreen(
            onLoginClick = {},
            onSignUpClick = {},
            onPrivacyClick = {},
            onPolicyClick = {}
        )
    }
}