package ipca.pdm.myshoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import ipca.pdm.myshoppinglist.ui.home.AddListTypeView
import ipca.pdm.myshoppinglist.ui.home.ListTypesView
import ipca.pdm.myshoppinglist.ui.login.LoginView
import ipca.pdm.myshoppinglist.ui.theme.MyShoppingListTheme

const val TAG = "myshoppinglist"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var navController = rememberNavController()

            MyShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   NavHost(
                       modifier = Modifier.padding(innerPadding),
                       navController = navController,
                       startDestination = Screen.Login.route) {
                       composable(Screen.Login.route) {
                           LoginView{
                               navController.navigate(Screen.ListTypes.route)
                           }
                       }
                       composable(Screen.ListTypes.route) {
                           ListTypesView(){
                               navController.navigate(Screen.AddListType.route)
                           }
                       }
                       composable(Screen.AddListType.route) {
                           AddListTypeView(
                               navController = navController
                           )
                       }
                   }
                }
            }

            LaunchedEffect(Unit) {

                val auth = Firebase.auth

                val currentUser = auth.currentUser
                if (currentUser != null) {
                    navController.navigate(Screen.ListTypes.route)
                }
            }
        }
    }
}

sealed class Screen(val route : String){
    object Login : Screen("login")
    object ListTypes : Screen("list_types")
    object AddListType : Screen("add_list_type")
}
