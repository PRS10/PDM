package hoods.com.notes.repository

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hoods.com.notes.models.Notes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val NOTES_COLLECTION_REF = "notes"


class StorageRepository {
    private val auth = Firebase.auth
    fun user() = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val notesRef: CollectionReference = Firebase
        .firestore.collection(NOTES_COLLECTION_REF)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getUserNotes(userId: String): Flow<Resources<List<Notes>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null

        try {
            // Simplificando a query temporariamente
            snapshotStateListener = notesRef
                .whereArrayContains("owners", userId)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val notes = snapshot.toObjects(Notes::class.java)
                        Resources.Success(data = notes)
                    } else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)
                }
        } catch (e: Exception) {
            trySend(Resources.Error(e.cause))
            e.printStackTrace()
        }

        awaitClose {
            snapshotStateListener?.remove()
        }
    }
    fun getNote(
        noteId:String,
        onError:(Throwable?) -> Unit,
        onSuccess: (Notes?) -> Unit
    ){
        notesRef
            .document(noteId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(Notes::class.java))
            }
            .addOnFailureListener {result ->
                onError.invoke(result.cause)
            }


    }

    fun addNote(
        userId: String,
        title: String,
        description: String,
        timestamp: Timestamp,
        color: Int = 0,
        isFavorite: Boolean,
        onComplete: (Boolean) -> Unit,
    ){
        val documentId = notesRef.document().id
        val note = Notes(
            userId,
            title,
            description,
            timestamp,
            color = color,
            documentId = documentId,
            owners = listOf(userId),
            isFavorite = isFavorite
        )
        notesRef
            .document(documentId)
            .set(note)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }


    }

    fun deleteNote(noteId: String,onComplete: (Boolean) -> Unit){
        notesRef.document(noteId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }

    fun updateNote(
        title: String,
        note:String,
        color: Int,
        noteId: String,
        isFavorite: Boolean,
        onResult:(Boolean) -> Unit
    ){
        val updateData = hashMapOf<String,Any>(
            "color" to color,
            "description" to note,
            "title" to title,
            "isFavorite" to isFavorite
        )

        notesRef.document(noteId)
            .update(updateData)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    Log.d("updateNote", "Update successful")
                    onResult(true)
                } else {
                    Log.e("updateNote", "Update failed", task.exception)
                    onResult(false)
                }




            }



    }

    fun signOut() = Firebase.auth.signOut()

    fun observeUser(onUserChanged: (FirebaseUser?) -> Unit) {
        auth.addAuthStateListener { firebaseAuth ->
            onUserChanged(firebaseAuth.currentUser)
        }
    }

    fun addOwnerToNote(
        noteId: String,
        userId: String,
        onComplete: (Boolean) -> Unit
    ) {
        notesRef.document(noteId)
            .update("owners", FieldValue.arrayUnion(userId)) // Usa arrayUnion para adicionar sem duplicar
            .addOnCompleteListener { task ->
                onComplete.invoke(task.isSuccessful)
            }
    }

    fun shareNoteWithUser(note: Notes, userId: String) {
        val updatedOwners = note.owners.toMutableList().apply { add(userId) }
        val updateData = mapOf("owners" to updatedOwners)

        FirebaseFirestore.getInstance()
            .collection("notes")
            .document(note.documentId)
            .update(updateData)
            .addOnSuccessListener {
                Log.d("UserSearchPage", "Note shared successfully with user: $userId")
            }
            .addOnFailureListener { e ->
                Log.e("UserSearchPage", "Error sharing note: ${e.localizedMessage}")
            }
    }

}


sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null,
) {
    class Loading<T> : Resources<T>()
    class Success<T>(data: T?) : Resources<T>(data = data)
    class Error<T>(throwable: Throwable?) : Resources<T>(throwable = throwable)

}






























