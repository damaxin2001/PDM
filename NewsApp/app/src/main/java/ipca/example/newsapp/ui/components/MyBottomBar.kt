package ipca.example.newsapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.newsapp.Screen
import ipca.example.newsapp.ui.theme.NewsAppTheme


data class BottomNavigationItem (
    val title : String,
    val selectedIcon : ImageVector,
    val unSelectedIcon : ImageVector,
    val screen : Screen
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomBar(navController: NavController ) {

    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            screen = Screen.Home
        ),
        BottomNavigationItem(
            title = "Bookmarks",
            selectedIcon = Icons.Filled.Favorite,
            unSelectedIcon = Icons.Filled.FavoriteBorder,
            screen = Screen.Bookmarks
        ),

        )

    BottomAppBar {
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        navController.navigate(item.screen.route)
                    },
                    label = {
                        Text(item.title)

                    },
                    alwaysShowLabel = true,
                    icon = {
                        Icon(
                            imageVector = if (index == selectedItem){
                                item.selectedIcon
                            }else{
                                item.unSelectedIcon
                            },
                            contentDescription = item.title
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyBottomBarPreview(){
    NewsAppTheme {
        MyBottomBar(navController = rememberNavController())
    }
}
