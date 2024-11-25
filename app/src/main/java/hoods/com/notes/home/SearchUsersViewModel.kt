package hoods.com.notes.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class SearchUsersUiState(
    val searchQuery: String = "",
    val users: List<String> = emptyList(),
    val filteredUsers: List<String> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class SearchUsersViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    var state = mutableStateOf(SearchUsersUiState())
        private set

    init {
        fetchUsers()
    }

    // Busca a lista de utilizadores do Firestore
    private fun fetchUsers() {
        state.value = state.value.copy(isLoading = true, error = null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestore.collection("users")
                    .get()
                    .await()
                    .map { it.getString("email") ?: "" }
                    .filter { it.isNotEmpty() }

                state.value = state.value.copy(
                        users = result,
                        filteredUsers = result,
                        isLoading = false
                )
            } catch (e: Exception) {
                state.value = state.value.copy(
                        isLoading = false,
                        error = e.message ?: "Erro ao carregar utilizadores"
                )
            }
        }
    }

    // Atualiza o texto da pesquisa e filtra os utilizadores
    fun updateSearchQuery(query: String) {
        val currentState = state.value
        val filtered = currentState.users.filter {
            it.contains(query, ignoreCase = true)
        }
        state.value = currentState.copy(
                searchQuery = query,
                filteredUsers = filtered
        )
    }
}