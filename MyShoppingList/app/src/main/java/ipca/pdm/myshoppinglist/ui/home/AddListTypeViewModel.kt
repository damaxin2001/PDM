package ipca.pdm.myshoppinglist.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ipca.pdm.myshoppinglist.TAG
import ipca.pdm.myshoppinglist.models.ListItem
import ipca.pdm.myshoppinglist.repositories.AddItemRepository
import ipca.pdm.myshoppinglist.repositories.ListItemRepository

data class AddListTypeState(
    var name: String = "",
    var description: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class AddListTypeViewModel : ViewModel() {

    var state = mutableStateOf(AddListTypeState())
        private set

    private val name
        get() = state.value.name
    private val description
        get() = state.value.description

    fun onNameChange(newValue: String) {
        state.value = state.value.copy(name = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        state.value = state.value.copy(description = newValue)
    }

    fun addList(onSuccess: () -> Unit) {
        val userId = Firebase.auth.currentUser?.uid ?: return
        val listItem = ListItem(
            id = "",
            name = name,
            description = description,
            userId = userId
        )

        AddItemRepository.add(listItem) {
            onSuccess()
        }
    }
}