package ipca.aulas.myshoppinglist.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ipca.aulas.myshoppinglist.ui.theme.MyShoppingListTheme


@Composable
fun AddListTypesView(modifier: Modifier = Modifier){

    val viewModel by remember { mutableStateOf(AddListTypesViewModel()) }
    val state = viewModel.state


    Box(modifier = modifier.fillMaxSize() ){
        Button(onClick = {
            viewModel.addList {  }
        }) {
            Text(text = "Add new list")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddListTypesViewPreview() {
    MyShoppingListTheme {
        AddListTypesView()
    }
}