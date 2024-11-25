//package hoods.com.notes
//
//import Home
//import android.util.Log
//import androidx.compose.runtime.Composable
//import androidx.navigation.*
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import hoods.com.notes.detail.DetailScreen
//import hoods.com.notes.detail.DetailViewModel
//import hoods.com.notes.home.Home
//import hoods.com.notes.home.HomeViewModel
//import hoods.com.notes.login.LoginScreen
//import hoods.com.notes.login.LoginViewModel
//import hoods.com.notes.login.SignUpScreen
//
//enum class LoginRoutes {
//    Signup,
//    SignIn
//}
//
//enum class HomeRoutes {
//    Home,
//    Detail
//}
//
//enum class NestedRoutes {
//    Main,
//    Login
//}
//
//
//@Composable
//fun Navigation(
//    navController: NavHostController = rememberNavController(),
//    loginViewModel: LoginViewModel,
//    detailViewModel: DetailViewModel,
//    homeViewModel: HomeViewModel
//) {
//    NavHost(
//        navController = navController,
//        startDestination = NestedRoutes.Main.name
//    ) {
//        authGraph(navController, loginViewModel)
//        homeGraph(
//            navController = navController,
//            detailViewModel,
//            homeViewModel
//        )
//    }
//
//
//}
//
//fun NavGraphBuilder.authGraph(
//    navController: NavHostController,
//    loginViewModel: LoginViewModel,
//) {
//    navigation(
//        startDestination = LoginRoutes.SignIn.name,
//        route = NestedRoutes.Login.name
//    ) {
//        composable(route = LoginRoutes.SignIn.name) {
//            LoginScreen(onNavToHomePage = {
//                navController.navigate(NestedRoutes.Main.name) {
//                    launchSingleTop = true
//                    popUpTo(route = LoginRoutes.SignIn.name) {
//                        inclusive = true
//                    }
//                }
//            },
//                loginViewModel = loginViewModel
//
//            ) {
//                navController.navigate(LoginRoutes.Signup.name) {
//                    launchSingleTop = true
//                    popUpTo(LoginRoutes.SignIn.name) {
//                        inclusive = true
//                    }
//                }
//            }
//        }
//
//        composable(route = LoginRoutes.Signup.name) {
//            SignUpScreen(onNavToHomePage = {
//                navController.navigate(NestedRoutes.Main.name) {
//                    popUpTo(LoginRoutes.Signup.name) {
//                        inclusive = true
//                    }
//                }
//            },
//                loginViewModel = loginViewModel
//            ) {
//                navController.navigate(LoginRoutes.SignIn.name)
//            }
//
//        }
//
//    }
//
//}
//
//fun NavGraphBuilder.homeGraph(
//    navController: NavHostController,
//    detailViewModel: DetailViewModel,
//    homeViewModel: HomeViewModel
//){
//    navigation(
//        startDestination = HomeRoutes.Home.name,
//        route = NestedRoutes.Main.name,
//    ){
//        composable(HomeRoutes.Home.name){
//            Home(
//                homeViewModel = homeViewModel,
//                onNoteClick = { noteId ->
//                    navController.navigate(
//                        HomeRoutes.Detail.name + "?id=$noteId"
//                    ){
//                        launchSingleTop = true
//                    }
//                },
//
//                navToDetailPage = {
//                    navController.navigate(HomeRoutes.Detail.name)
//                },
//
//
//            ) {
//                navController.navigate(NestedRoutes.Login.name){
//                    launchSingleTop = true
//                    popUpTo(0){
//                        inclusive = true
//                    }
//                }
//
//            }
//        }
//
//        composable(
//            route = HomeRoutes.Detail.name + "?id={id}",
//            arguments = listOf(navArgument("id"){
//                type = NavType.StringType
//                defaultValue = ""
//            })
//        ){ entry ->
//
//            DetailScreen(
//                detailViewModel = detailViewModel,
//                noteId = entry.arguments?.getString("id") as String,
//            ) {
//                navController.navigateUp()
//            }
//
//
//        }
//
//
//
//    }
//
//
//
//
//}
//
//
//
//
//
//
//


package hoods.com.notes

import Home
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hoods.com.notes.detail.DetailScreen
import hoods.com.notes.detail.DetailViewModel
import hoods.com.notes.home.HomeViewModel
import hoods.com.notes.home.UserSearchPage
import hoods.com.notes.login.LoginScreen
import hoods.com.notes.login.LoginViewModel
import hoods.com.notes.login.SignUpScreen

enum class LoginRoutes {
    Signup,
    SignIn
}

enum class HomeRoutes {
    Home,
    Detail,
    SearchUser // Adicione a rota SearchUser aqui
}

enum class NestedRoutes {
    Main,
    Login
}


@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,
    detailViewModel: DetailViewModel,
    homeViewModel: HomeViewModel
) {
    NavHost(
            navController = navController,
            startDestination = NestedRoutes.Main.name
    ) {
        authGraph(navController, loginViewModel)
        homeGraph(
                navController = navController,
                detailViewModel,
                homeViewModel
        )
    }
}

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
) {
    navigation(
            startDestination = LoginRoutes.SignIn.name,
            route = NestedRoutes.Login.name
    ) {
        composable(route = LoginRoutes.SignIn.name) {
            LoginScreen(onNavToHomePage = {
                navController.navigate(NestedRoutes.Main.name) {
                    launchSingleTop = true
                    popUpTo(route = LoginRoutes.SignIn.name) {
                        inclusive = true
                    }
                }
            },
                        loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.Signup.name) {
                    launchSingleTop = true
                    popUpTo(LoginRoutes.SignIn.name) {
                        inclusive = true
                    }
                }
            }
        }

        composable(route = LoginRoutes.Signup.name) {
            SignUpScreen(onNavToHomePage = {
                navController.navigate(NestedRoutes.Main.name) {
                    popUpTo(LoginRoutes.Signup.name) {
                        inclusive = true
                    }
                }
            },
                         loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.SignIn.name)
            }
        }
    }
}

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    detailViewModel: DetailViewModel,
    homeViewModel: HomeViewModel
) {
    navigation(
            startDestination = HomeRoutes.Home.name,
            route = NestedRoutes.Main.name,
    ) {
        composable(HomeRoutes.Home.name) {
            Home(
                    homeViewModel = homeViewModel,
                    onNoteClick = { noteId ->
                        navController.navigate(HomeRoutes.Detail.name + "?id=$noteId")
                    },
                    navToDetailPage = {
                        navController.navigate(HomeRoutes.Detail.name)
                    },
                    navToLoginPage = {
                        navController.navigate(NestedRoutes.Login.name) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    navToSearchUserPage = {
                        navController.navigate(HomeRoutes.SearchUser.name) // Corrigido aqui
                    }
            )
        }

        composable(
                route = HomeRoutes.Detail.name + "?id={id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                })
        ) { entry ->
            DetailScreen(
                    detailViewModel = detailViewModel,
                    noteId = entry.arguments?.getString("id") ?: "",
            ) {
                navController.navigateUp()
            }
        }

        // Adicionei a rota de procura de utilizador aqui
        composable(HomeRoutes.SearchUser.name) {
            UserSearchPage() // Tela de procura de utilizador
        }
    }
}