package ipca.aulas.myshoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import ipca.aulas.myshoppinglist.ui.theme.MyShoppingListTheme
import ipca.aulas.myshoppinglist.navigation.NavigationShoppingList

const val TAG = "myshoppinglist"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var navController = rememberNavController()
            MyShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    NavHost(
//                        modifier = Modifier.padding(innerPadding),
//                        navController = navController,
//                        startDestination = "login") {
//                        composable("login") {
//                            LoginView{
//                                navController.navigate("home")
//                            }
//                        }
//                        composable("home") {
//                            AddListTypesView()
//                        }
//                    }

                    NavigationShoppingList(
                        navController = navController
                    )


                }
            }
        }
    }
}