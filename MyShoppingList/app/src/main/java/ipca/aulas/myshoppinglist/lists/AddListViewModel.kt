package ipca.aulas.myshoppinglist.lists

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.aulas.myshoppinglist.TAG

data class AddListState(

    val isLoading: Boolean = false,
    val error: String? = null
)

class AddListTypesViewModel : ViewModel() {

    var state = mutableStateOf(AddListState())
        private set

    fun addList(){
        ListItemRepository.add(
            listItem = ListItem(name = "te", description = "des"),{})

    }

    fun addloadListTypes()
    {
        ListItemRepository.getAll { listItems ->
            for (item in listItems){
                Log.d("a", item.name ?: "no name")
            }
        }
    }
}