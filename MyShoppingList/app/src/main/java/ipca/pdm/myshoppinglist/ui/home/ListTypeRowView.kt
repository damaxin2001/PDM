package ipca.pdm.myshoppinglist.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ipca.pdm.myshoppinglist.models.ListItem
import ipca.pdm.myshoppinglist.ui.theme.MyShoppingListTheme
import ipca.pdm.myshoppinglist.ui.theme.appFontBold16

@Composable
fun ListTypeRowView(
    modifier: Modifier = Modifier,
    listItem: ListItem,
    onDelete: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = listItem.name ?: "",
                style = appFontBold16
            )
            Text(
                text = listItem.description ?: ""
            )
        }

        IconButton(
            onClick = onDelete,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",
                tint = Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListTypeRowViewPreview() {
    MyShoppingListTheme {
        ListTypeRowView(
            listItem = ListItem(
                id = "1",
                name = "Compras de casa",
                description = "As compras que são para casa",
                userId = "userId"
            ),
            onDelete = {} // Função de callback vazia para o preview
        )
    }
}