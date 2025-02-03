package ipca.pdm.myshoppinglist.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ipca.pdm.myshoppinglist.models.ListItem

@Composable
fun DeleteListTypeView(
    modifier: Modifier = Modifier,
    listId: String, // Recebe apenas o ID
    navController: NavController,
    onDeleteSuccess: () -> Unit
) {
    val viewModel: DeleteListTypeViewModel = viewModel()
    val state = viewModel.state

    // Carrega os detalhes da lista ao abrir a tela
    LaunchedEffect(key1 = listId) {
        viewModel.loadListDetails(listId)
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Tem certeza que deseja excluir a lista '${state.value.listItem?.name}'?")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.deleteList(listId) {
                    onDeleteSuccess()
                    navController.popBackStack()
                }
            }) {
                Text(text = "Excluir")
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (state.value.isLoading) {
                CircularProgressIndicator()
            }
            state.value.error?.let {
                Text(text = it, color = Color.Red)
            }
        }
    }
}