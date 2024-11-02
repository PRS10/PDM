package ipca.aulas.productsapp

import android.os.Bundle
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import ipca.aulas.productsapp.data.product.ProductsRepImplementation
import ipca.aulas.productsapp.data.product.model.ProductX
import ipca.aulas.productsapp.navigation.MyNav
import ipca.aulas.productsapp.presentation.ProductsScreen
import ipca.aulas.productsapp.presentation.ProductsViewModel
import ipca.aulas.productsapp.ui.theme.ProductsAPPTheme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ProductsViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductsViewModel(ProductsRepImplementation(RetrofitInstancia.api)) as T
            }
        }
    })

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductsAPPTheme {
                val navController = rememberNavController() // Inicia o NavHostController

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Produtos!")
                            }
                        )
                    },
                    bottomBar = {
                        BottomAppBar {
                            // Ícone Home alinhado à esquerda
                            IconButton(
                                onClick = { /* TODO */ }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "Home"
                                )
                            }

                            // Espaço flexível para empurrar o próximo ícone para o centro
                            Spacer(modifier = Modifier.weight(1f))

                            // Ícone para uma ação central (se necessário)
                            IconButton(
                                onClick = { /* TODO */ }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Favorite"
                                )
                            }

                            // Outro espaço flexível para empurrar o próximo ícone para a direita
                            Spacer(modifier = Modifier.weight(1f))

                            // Ícone Settings alinhado à direita
                            IconButton(
                                onClick = { /* TODO */ }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Settings"
                                )
                            }
                        }
                    }

                ) { paddingValues -> // Recebe o paddingValues aqui
                    MyNav(navHostController = navController, viewModel = viewModel)

                }
            }
        }
    }
}





