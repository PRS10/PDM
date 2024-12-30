package hoods.com.notes.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchTextField(
    query: String,
    onQueryChange: (String) -> Unit
) {
    OutlinedTextField(
            value = query,
            onValueChange = { onQueryChange(it) },
            leadingIcon = {
//            Icon(
//                imageVector = Icons.Default.Search,
//                contentDescription = "Search Icon"
//            )
        },
            placeholder = { androidx.compose.material3.Text(text = "Procurar...") },
            modifier = Modifier.fillMaxWidth()
                .padding(4.dp),
    )
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SearchTextFieldPreview() {
    var query by remember { mutableStateOf(TextFieldValue("")) }
    SearchTextField(
        query = query.text,
        onQueryChange = { query = TextFieldValue(it) }
    )
}