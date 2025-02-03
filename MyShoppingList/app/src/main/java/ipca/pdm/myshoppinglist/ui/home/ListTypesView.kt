package ipca.pdm.myshoppinglist.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ipca.pdm.myshoppinglist.R
import ipca.pdm.myshoppinglist.models.ListItem
import ipca.pdm.myshoppinglist.ui.theme.MyShoppingListTheme


@Composable
fun ListTypesView(
    modifier: Modifier = Modifier,
    onNavigateToAddList: () -> Unit,
    onNavigateToDeleteList: (String) -> Unit
) {
    val viewModel: ListTypesViewModel = viewModel()
    val state = viewModel.state

    ListTypesViewContent(
        modifier = modifier,
        state = state.value,
        onNavigateToAddList = onNavigateToAddList,
        onNavigateToDeleteList = onNavigateToDeleteList
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.loadListTypes()
    }
}

@Composable
fun ListTypesViewContent(
    modifier: Modifier = Modifier,
    state: ListState,
    onNavigateToAddList: () -> Unit = {},
    onNavigateToDeleteList: (String) -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                itemsIndexed(
                    items = state.listItems
                ) { _, item ->
                    ListTypeRowView(
                        listItem = item,
                        onDelete = {
                            if (item.id.isNotEmpty()) {
                                onNavigateToDeleteList(item.id) // Passa o ID da lista
                            }
                        }
                    )
                }
            }
        }

        Button(
            modifier = Modifier
                .padding(16.dp)
                .size(80.dp),
            onClick = { onNavigateToAddList() }
        ) {
            Image(
                modifier = Modifier.size(60.dp),
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "add list",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddListTypesViewPreview() {
    MyShoppingListTheme {
        ListTypesViewContent(
            state =
            ListState(listItems = arrayListOf(
                ListItem("", "Compras de Casa", "As compras que vão para casa" , null),
                ListItem("", "Compras de Escritório", "As compras que vão para o trabalho", null)

            )
            )
        )
    }
}