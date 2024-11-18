package ipca.pdm.myshoppinglist.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ipca.pdm.myshoppinglist.models.ListItem
import ipca.pdm.myshoppinglist.repositories.ListItemRepository

data class ListState(
    val listItems : List<ListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ListTypesViewModel : ViewModel() {

    var state = mutableStateOf(ListState())
        private set

    fun addList() {
        ListItemRepository.add(
            ListItem("","title", "description")
        ){

        }
    }

    fun loadListTypes(){
        ListItemRepository.getAll { listItems ->
            state.value = state.value.copy(
                listItems = listItems
            )

            for (item in listItems){
                Log.d("TAG", item.name?:"no name")
            }
        }
    }

}