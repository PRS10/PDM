package hoods.com.notes.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hoods.com.notes.models.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSearchPage(
    modifier: Modifier = Modifier,
    documentId: String,
    onShareComplete: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    val viewModel: SearchUsersViewModel = viewModel()
    val state by viewModel.state
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    fun showToast(message: String) {
        scope.launch(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("Share with Users") },
                        actions = {
                            IconButton(
                                    onClick = {
                                        if (state.selectedUsers.isNotEmpty()) {
                                            viewModel.shareWithSelectedUsers(
                                                    documentId = documentId,
                                                    selectedUsers = state.selectedUsers
                                            ) { success ->
                                                scope.launch(Dispatchers.Main) {
                                                    if (success) {
                                                        showToast("Note shared successfully")
                                                    } else {
                                                        showToast("Error sharing note")
                                                    }
                                                    onShareComplete(success)
                                                }
                                            }
                                        }
                                    },
                                    enabled = state.selectedUsers.isNotEmpty()
                            ) {
                                Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = "Share"
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = onDismiss) {
                                Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close"
                                )
                            }
                        }
                )
            }
    ) { paddingValues ->
        Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
        ) {
            OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = { query -> viewModel.updateSearchQuery(query) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    label = { Text("Search Users") }
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
                        Text(text = "Error: ${state.error}")
                    }
                }
                state.filteredUsers.isEmpty() -> {
                    Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No users found")
                    }
                }
                else -> {
                    LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.filteredUsers.size) { index ->
                            val userInfo = state.filteredUsers[index]
                            Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { viewModel.toggleUserSelection(userInfo.id) }
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                        checked = state.selectedUsers.contains(userInfo.id),
                                        onCheckedChange = { viewModel.toggleUserSelection(userInfo.id) }
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                        text = userInfo.email,
                                        style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}