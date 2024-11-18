package ipca.pdm.myshoppinglist.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.pdm.myshoppinglist.models.ListItem
import ipca.pdm.myshoppinglist.ui.login.LoginViewModel
import ipca.pdm.myshoppinglist.ui.theme.MyShoppingListTheme
import ipca.pdm.myshoppinglist.ui.theme.appFontBold16

@Composable
fun AddListTypeView(modifier: Modifier = Modifier,
                    navController : NavController = rememberNavController()
) {

    val viewModel : AddListTypeViewModel = viewModel()
    val state = viewModel.state

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            TextField(value = state.value.name,
                onValueChange = viewModel::onNameChange,
                placeholder = {
                    Text(text = "list name")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = state.value.description,
                onValueChange = viewModel::onDescriptionChange,
                placeholder = {
                    Text(text = "list description")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.addList()
                // todo : back
                navController.popBackStack()
            }) {
                Text(text = "Add")
            }
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddListTypeViewPreview(){
    MyShoppingListTheme {
        AddListTypeView()
    }
}