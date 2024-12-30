package hoods.com.notes.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchUsersViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    var state = mutableStateOf(SearchUsersUiState())
        private set

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        state.value = state.value.copy(isLoading = true, error = null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestore.collection("users")
                    .get()
                    .await()
                    .map { document ->
                        UserInfo(
                                id = document.id,  // Pegamos o ID do documento
                                email = document.getString("email") ?: ""
                        )
                    }
                    .filter { it.email.isNotEmpty() }

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

    fun shareWithSelectedUsers(
        documentId: String,
        selectedUsers: Set<String>,  // Agora isso contém IDs de usuário
        onComplete: (Boolean) -> Unit
    ) {
        if (selectedUsers.isEmpty()) {
            onComplete(false)
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val docRef = firestore.collection("notes").document(documentId)
                val document = docRef.get().await()

                if (document.exists()) {
                    val currentOwners = document.get("owners") as? List<String> ?: listOf()
                    val updatedOwners = (currentOwners + selectedUsers).distinct()

                    docRef.update("owners", updatedOwners).await()
                    onComplete(true)
                } else {
                    Log.e("ShareError", "Document $documentId not found")
                    onComplete(false)
                }
            } catch (e: Exception) {
                Log.e("ShareError", "Error sharing note", e)
                onComplete(false)
            }
        }
    }

    fun updateSearchQuery(query: String) {
        val currentState = state.value
        val filtered = currentState.users.filter {
            it.email.contains(query, ignoreCase = true)
        }
        state.value = currentState.copy(
                searchQuery = query,
                filteredUsers = filtered
        )
    }

    fun toggleUserSelection(userId: String) {
        val currentSelected = state.value.selectedUsers.toMutableSet()
        if (currentSelected.contains(userId)) {
            currentSelected.remove(userId)
        } else {
            currentSelected.add(userId)
        }
        state.value = state.value.copy(selectedUsers = currentSelected)
    }
}

// Classe para representar as informações do usuário
data class UserInfo(
    val id: String,
    val email: String
)

// Estado atualizado para usar UserInfo
data class SearchUsersUiState(
    val searchQuery: String = "",
    val users: List<UserInfo> = emptyList(),
    val filteredUsers: List<UserInfo> = emptyList(),
    val selectedUsers: Set<String> = emptySet(),  // Mantemos os IDs dos usuários selecionados
    val isLoading: Boolean = true,
    val error: String? = null
)