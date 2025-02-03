package ipca.pdm.myshoppinglist.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ipca.pdm.myshoppinglist.models.ListItem
import ipca.pdm.myshoppinglist.repositories.ListItemRepository

data class ListState(
    val listItems: List<ListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ListTypesViewModel : ViewModel() {

    var state = mutableStateOf(ListState())
        private set

    fun loadListTypes() {
        val userId = Firebase.auth.currentUser?.uid ?: return
        ListItemRepository.getAll(userId) { listItems ->
            state.value = state.value.copy(
                listItems = listItems
            )

            for (item in listItems) {
                Log.d("TAG", item.name ?: "no name")
            }
        }
    }
}