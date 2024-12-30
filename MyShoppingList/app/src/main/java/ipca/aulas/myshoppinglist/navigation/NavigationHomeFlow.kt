package ipca.aulas.myshoppinglist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ipca.aulas.myshoppinglist.lists.AddListTypesView

fun NavGraphBuilder.mainFlow(navController: NavHostController) {
    navigation(
        startDestination = Route.Home.route,
        route = Flow.Main.route
    ) {
        composable(Route.Home.route) {
            AddListTypesView()
        }
    }
}