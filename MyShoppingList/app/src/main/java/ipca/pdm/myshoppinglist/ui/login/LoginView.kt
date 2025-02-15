package ipca.pdm.myshoppinglist.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ipca.pdm.myshoppinglist.ui.theme.MyShoppingListTheme
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun LoginView(modifier: Modifier = Modifier,
                onLoginSuccess: () -> Unit = {},
                onNavigateToRegister: () -> Unit = {}
              ) {

    val viewModel : LoginViewModel = viewModel()
    val state = viewModel.state

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(value = state.value.username,
                onValueChange = viewModel::onUsernameChange,
                placeholder = {
                    Text(text = "email")
                }

            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = state.value.password,
                onValueChange = viewModel::onPasswordChange,
                placeholder = {
                    Text(text = "password")
                },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.login(onLoginSuccess = onLoginSuccess)
            }) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = state.value.error ?: "")
            if (state.value.isLoading)
                CircularProgressIndicator()
            Button(onClick = onNavigateToRegister) {
                Text(text = "Don't have an account? Register")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    MyShoppingListTheme {
        LoginView()
    }
}
