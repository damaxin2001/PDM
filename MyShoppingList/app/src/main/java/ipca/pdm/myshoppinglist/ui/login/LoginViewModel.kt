package ipca.pdm.myshoppinglist.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ipca.pdm.myshoppinglist.TAG


data class LoginState(
    var username: String = "",
    var password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class LoginViewModel : ViewModel() {

    var state = mutableStateOf(LoginState())
        private set

    private val username
        get() = state.value.username
    private val password
        get() = state.value.password

    fun onUsernameChange(newValue: String) {
        state.value = state.value.copy(username = newValue)
    }

    fun onPasswordChange(newValue: String) {
        state.value = state.value.copy(password = newValue)
    }

    fun login( onLoginSuccess: () -> Unit) {

        if (username.isEmpty()) {
            state.value = state.value.copy(error = "Email is required")
            return
        }
        if (password.isEmpty()) {
            state.value = state.value.copy(error = "Password is required")
            return
        }

        state.value = state.value.copy(isLoading = true)
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword( state.value.username, state.value.password)
            .addOnCompleteListener { task ->
                state.value = state.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    state.value = state.value.copy(error = null)
                    onLoginSuccess()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    state.value = when (task.exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            state.value.copy(error = "Invalid email or password")
                        }

                        is FirebaseAuthInvalidUserException -> {
                            state.value.copy(error = "User not found")
                        }

                        else -> {
                            state.value.copy(error = task.exception.toString())
                        }
                    }
                }
            }
    }

}