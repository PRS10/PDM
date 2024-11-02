package ipca.aulas.productsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import androidx.navigation.navigation
import ipca.aulas.productsapp.presentation.ProductsScreen
import ipca.aulas.productsapp.presentation.ProductsViewModel
import ipca.aulas.productsapp.screens.produto.ProductDetailScreen

sealed class Route(val name: String) {
    object ProductsScreen : Route("products")
    object DetailProduct : Route("detailProduct/{productId}") {
        fun createRoute(productId: String) = "detailProduct/$productId"
    }
}

@Composable
fun MyNav(
    navHostController: NavHostController,
    viewModel: ProductsViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = "flow_navegação"
    ) {
        navigation(
            startDestination = Route.ProductsScreen.name,
            route ="flow_navegação"
        ){
        composable(route = Route.ProductsScreen.name) {
            ProductsScreen(
                viewModel = viewModel,
                onProductClick = { productId ->
                    navHostController.navigate(Route.DetailProduct.createRoute(productId))
                }
            )
        }

        composable(
            route = Route.DetailProduct.name,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Obtemos o ID do produto dos argumentos
            val productId = backStackEntry.arguments?.getString("productId")
            // Procura o produto no ViewModel utilizando o ID
            productId?.let { // Funciona tambem se passar varios argumentos.
                val product = viewModel.getProductById(it)
                if (product != null) {
                    ProductDetailScreen(product = product)
                }
            }
        }
        }
    }
}