package ipca.aulas.myshoppinglist.navigation

import androidx.compose.ui.window.Popup
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ipca.aulas.myshoppinglist.login.LoginView
import ipca.aulas.myshoppinglist.popup.AutoOpenPopupExample
import ipca.aulas.myshoppinglist.signup.SignUpView

//mails já registados:
//vamos@gmail.com
// pedroricardo10@gmail.com
//123456

fun NavGraphBuilder.authFlow(navController: NavHostController) {
    navigation(
        startDestination = Route.Login.route,
        route = Flow.Auth.route
    ) {
        composable(Route.Login.route) {
            LoginView(
                onLoginSuccess = {
                    navController.navigate(Flow.Main.route) {
                        popUpTo(Flow.Auth.route) { inclusive = true }
                    }
                },
                onSignUpClick = {
                    navController.navigate(Route.SignUp.route)
//                    navController.navigate(Route.SignUp.route)
                }
            )
        }
        composable(Route.SignUp.route) {
            SignUpView(
                onSignUpSuccess = {
                    navController.navigate(Flow.Auth.route) {
                        popUpTo(Flow.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Route.SignUpPopUp.route) {
            AutoOpenPopupExample(
                onClosePopup= {}
            )
        }

    }
}
