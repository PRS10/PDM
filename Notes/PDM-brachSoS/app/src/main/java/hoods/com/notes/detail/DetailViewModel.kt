package hoods.com.notes.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import hoods.com.notes.RoomApplication
import hoods.com.notes.data.room.models.Fav
import hoods.com.notes.models.Notes
import hoods.com.notes.repository.StorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext


data class DetailUiState(
    val colorIndex: Int = 0,
    val title: String = "",
    val note: String = "",
    val noteAddedStatus: Boolean = false,
    val updateNoteStatus: Boolean = false,
    val selectedNote: Notes? = null,
    val isFavorite: Boolean = true  // New field for favorite state
)



class DetailViewModel(
    private val repository: StorageRepository = StorageRepository(),
) : ViewModel() {
    var detailUiState by mutableStateOf(DetailUiState())
        private set

    private val itemDao = RoomApplication.db.itemDao()

    private val hasUser: Boolean
        get() = repository.hasUser()

    private val user: FirebaseUser?
        get() = repository.user()

    fun onColorChange(colorIndex: Int) {
        detailUiState = detailUiState.copy(colorIndex = colorIndex)
    }

    fun onTitleChange(title: String) {
        detailUiState = detailUiState.copy(title = title)
    }

    fun onNoteChange(note: String) {
        detailUiState = detailUiState.copy(note = note)
    }

    fun addNote() {
        if (hasUser) {
            repository.addNote(
                    userId = user!!.uid,
                    title = detailUiState.title,
                    description = detailUiState.note,
                    color = detailUiState.colorIndex,
                    isFavorite = detailUiState.isFavorite,
                    timestamp = Timestamp.now()
            ) {
                detailUiState = detailUiState.copy(noteAddedStatus = it)
            }
        }


    }

    fun setEditFields(note: Notes) {
        detailUiState = detailUiState.copy(
                colorIndex = note.color,
                title = note.title,
                note = note.description,
                isFavorite = note.isFavorite

        )

    }

    fun getNote(noteId: String) {
        repository.getNote(
                noteId = noteId,
                onError = {},
        ) {
            detailUiState = detailUiState.copy(selectedNote = it)
            setEditFields(it!!)


            detailUiState.selectedNote?.let { it1 -> setEditFields(it1) }
        }
    }

    fun updateNote(
        noteId: String
    ) {
        repository.updateNote(
                title = detailUiState.title,
                note = detailUiState.note,
                noteId = noteId,
                color = detailUiState.colorIndex,
                isFavorite = detailUiState.isFavorite
        ) {

            detailUiState = detailUiState.copy(updateNoteStatus = it)

        }
    }

    fun resetNoteAddedStatus() {
        detailUiState = detailUiState.copy(
                noteAddedStatus = false,
                updateNoteStatus = false,
        )
    }

    fun resetState() {
        detailUiState = DetailUiState()
    }

    private fun onFavoriteChange(isFavorite: Boolean) {
        detailUiState = detailUiState.copy(isFavorite = isFavorite)
    }

    fun checkFavoriteStatus(noteId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = user
            if (user != null) {
                Log.d("DetailViewModel","DEBUG: Checking favorite status for noteId: $noteId and userId: ${user.uid}")
                val fav = itemDao.getFavByUserAndNote(user.uid, noteId).firstOrNull()
                Log.d("DetailViewModel","DEBUG: Found favorite: $fav")

                withContext(Dispatchers.Main) {
                    onFavoriteChange(fav?.favorite ?: false)
                }
            }
        }
    }

    fun toggleFavorite(noteId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = user
            if (user != null) {
                Log.d("DetailViewModel","DEBUG: Toggling favorite for noteId: $noteId and userId: ${user.uid}")
                val fav = itemDao.getFavByUserAndNote(user.uid, noteId).firstOrNull()

                if (fav == null) {
                    val newFav = Fav(
                            id = 0,
                            userID = user.uid,
                            noteID = noteId,
                            favorite = true
                    )
                    Log.d("DetailViewModel","DEBUG: Inserting new favorite: $newFav")
                    itemDao.insert(newFav)
                } else {
                    Log.d("DetailViewModel","DEBUG: Found existing favorite: $fav")
                    val updatedFav = fav.copy(favorite = !fav.favorite)
                    Log.d("DetailViewModel","DEBUG: Updating to: $updatedFav")
                    itemDao.update(updatedFav)
                }

                withContext(Dispatchers.Main) {
                    onFavoriteChange(!detailUiState.isFavorite)
                }
            }
        }
    }


}















