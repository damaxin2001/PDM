package ipca.pdm.myshoppinglist.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ipca.pdm.myshoppinglist.TAG
import ipca.pdm.myshoppinglist.models.ListItem
import ipca.pdm.myshoppinglist.repositories.DeleteItemRepository
import ipca.pdm.myshoppinglist.repositories.ListItemRepository


data class DeleteListTypeState(
    val listItem: ListItem? = null, // Adiciona o item da lista
    val isLoading: Boolean = false,
    val error: String? = null
)

class DeleteListTypeViewModel : ViewModel() {

    var state = mutableStateOf(DeleteListTypeState())
        private set

    fun loadListDetails(listId: String) {
        state.value = state.value.copy(isLoading = true)
        ListItemRepository.getListById(listId,
            onSuccess = { listItem ->
                state.value = state.value.copy(listItem = listItem, isLoading = false)
            },
            onError = { error ->
                state.value = state.value.copy(isLoading = false, error = error)
            }
        )
    }

    fun deleteList(listItemId: String, onSuccess: () -> Unit) {
        if (listItemId.isEmpty()) {
            state.value = state.value.copy(error = "ID da lista inválido")
            return
        }

        state.value = state.value.copy(isLoading = true)
        DeleteItemRepository.delete(listItemId,
            onSuccess = {
                Log.d(TAG, "Lista deletada com sucesso")
                state.value = state.value.copy(isLoading = false)
                onSuccess()
            },
            onError = { error ->
                state.value = state.value.copy(isLoading = false, error = error)
            }
        )
    }
}