package hoods.com.notes.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hoods.com.notes.models.Notes
import hoods.com.notes.repository.Resources
import hoods.com.notes.repository.StorageRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

data class HomeUiState(
    val notesList: Resources<List<Notes>> = Resources.Loading(),
    val ownNotes: List<Notes> = emptyList(),
    val sharedNotes: List<Notes> = emptyList(),
    val favoriteNotes: List<Notes> = emptyList(),  // Nova lista para favoritos
    val noteDeletedStatus: Boolean = false,
)


class HomeViewModel(
    private val repository: StorageRepository = StorageRepository()
) : ViewModel() {
    var homeUiState by mutableStateOf(HomeUiState())
        private set

    // Criar um estado separado para o usuário
    var currentUser by mutableStateOf(repository.user())
        private set

    val hasUser: Boolean
        get() = repository.hasUser()
    private val userId: String
        get() = repository.getUserId()

    init {
        loadNotes()
        // Observar mudanças no usuário
        repository.observeUser { user ->
            currentUser = user
            if (user != null) {
                loadNotes()
            }
        }
    }

    fun loadNotes() {
        if (hasUser) {
            if (userId.isNotBlank()) {
                getUserNotes(userId)
            }
        } else {
            homeUiState = homeUiState.copy(notesList = Resources.Error(
                    throwable = Throwable(message = "User is not Login")
            ))
        }
    }

    private fun getUserNotes(userId: String) = viewModelScope.launch {
        repository.getUserNotes(userId).collect { result ->
            when (result) {
                is Resources.Success -> {
                    val notes = result.data ?: emptyList()
                    val ownNotes = notes.filter { it.userId == userId }
                    val sharedNotes = notes.filter { it.userId != userId }

                    homeUiState = homeUiState.copy(
                            notesList = result,
                            ownNotes = ownNotes,
                            sharedNotes = sharedNotes
                    )
                }
                else -> homeUiState = homeUiState.copy(notesList = result)
            }
        }
    }

    fun deleteNote(noteId: String) = repository.deleteNote(noteId) {
        homeUiState = homeUiState.copy(noteDeletedStatus = it)
    }

    fun signOut() = repository.signOut()
}






