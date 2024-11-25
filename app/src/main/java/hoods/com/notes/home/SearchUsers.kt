package hoods.com.notes.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSearchPage(
) {
    val viewModel: SearchUsersViewModel = viewModel()
    val state by viewModel.state

    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("Pesquisar Utilizadores") },
                        modifier = Modifier.fillMaxWidth()
                )
            }
    ) { paddingValues ->
        Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
        ) {
            // Campo de pesquisa
            OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = { query -> viewModel.updateSearchQuery(query) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    label = { Text("Procurar utilizadores") }
            )

            when {
                state.isLoading -> {
                    Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                state.error != null -> {
                    Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Erro: ${state.error}")
                    }
                }
                state.filteredUsers.isEmpty() -> {
                    Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Nenhum utilizador encontrado")
                    }
                }
                else -> {
                    LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.filteredUsers.size) { index ->
                            Text(
                                    text = state.filteredUsers[index],
                                    style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun UserSearchPagePreview() {
    UserSearchPage()
}