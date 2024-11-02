package empresa.tipodaapp.loginexemplo

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import empresa.tipodaapp.loginexemplo.ui.theme.HomeScreen
import empresa.tipodaapp.loginexemplo.ui.theme.login.LoginScreen
import empresa.tipodaapp.loginexemplo.ui.theme.login.SignInState
import empresa.tipodaapp.loginexemplo.ui.theme.signup.PolicyScreen
import empresa.tipodaapp.loginexemplo.ui.theme.signup.PrivacyScreen
import empresa.tipodaapp.loginexemplo.ui.theme.signup.SignUpScreen


// As rotas são feitas por texto, fica mais limpo e seguro se fizermos  uma sealed class
sealed class Route{
    data class LoginScreen(val name: String = "Login"):Route()
    data class SignUpScreen(val name: String = "SignUp"):Route()
    data class PolicyScreen(val name: String = "Policy"):Route()
    data class PrivacyScreen(val name: String = "Privacy"):Route()
    data class HomeScreen(val name: String = "Home"):Route()
}


@Composable
fun MyNavigation(navHostController: NavHostController){
    NavHost(
        navController = navHostController,
        startDestination = "login_flow", // O nome que chamei ao 'grupo' de navegação
    ){
        navigation(startDestination = Route.LoginScreen().name, route = "login_flow"){
            composable(route = Route.LoginScreen().name){
                LoginScreen(
                    state = SignInState(),
                    onLoginClick = {
                        navHostController.navigate(
                            Route.HomeScreen().name
                        ){ // quando entramos na home, removemos o login
                            popUpTo("login_flow")
                        }
                    },
                    onSignUpClick = {
                        navHostController.navigateToSingleTop(
                            Route.SignUpScreen().name
                        )
                    }
                )
            }
            composable(route = Route.SignUpScreen().name){
                SignUpScreen(
                    onSignUpClick = {
                        navHostController.navigate(
                            Route.HomeScreen().name
                        ){
                            popUpTo("login_flow")
                        }
                    },
                    onLoginClick = {
                        navHostController.navigateToSingleTop(
                            Route.LoginScreen().name
                        )
                    },
                    onPrivacyClick = {
                        navHostController.navigate(
                            Route.PrivacyScreen().name
                        ){
                            launchSingleTop = true // lança apenas uma instancia do backstak,
                        }
                    },
                    onPolicyClick = {
                        navHostController.navigate(
                            Route.PolicyScreen().name
                        ){
                            launchSingleTop = true // lança apenas uma instancia do backstak,
                        }
                    }
                )
            }
            composable(route = Route.PolicyScreen().name){
                PolicyScreen{
                    navHostController.navigateUp()
                }
            }
            composable(route = Route.PrivacyScreen().name){
                PrivacyScreen{
                    navHostController.navigateUp()
                }
            }
        }

        composable(route = Route.HomeScreen().name){
            HomeScreen()
        }
    }
}

// Esta extensao ajuda eliminar o backstack que existe quando clicamos em voltar
fun NavController.navigateToSingleTop(route:String){
    navigate(route){
        popUpTo(graph.findStartDestination().id){ // Esta linha elimina o backstack
            saveState = true // Esta linha guarda o estado com o stack limpo
        }
        launchSingleTop = true // lança apenas uma instancia do backstak,
        // se tiver, por exemplo varias vez o ecra de login, fica apenas um
        restoreState = true // Restaura o estado
    }
}