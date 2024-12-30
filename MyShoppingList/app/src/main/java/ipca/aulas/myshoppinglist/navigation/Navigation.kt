package ipca.aulas.myshoppinglist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost


sealed class Route(val route: String) {
    data object Login : Route("login")
    data object Home : Route("home")
    data object SignUp : Route("signup")
    data object SignUpPopUp : Route("signup_popup")
}

sealed class Flow(val route: String) {
    data object Auth : Flow("auth_flow")
    data object Main : Flow("main_flow")
}

@Composable
fun NavigationShoppingList(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Flow.Auth.route
    ) {
        authFlow(navController)
        mainFlow(navController)
    }
}


