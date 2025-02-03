package ipca.pdm.myshoppinglist.ui.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ipca.pdm.myshoppinglist.TAG

data class RegisterState(
    var username: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class RegisterViewModel : ViewModel() {

    var state = mutableStateOf(RegisterState())
        private set

    private val username
        get() = state.value.username
    private val password
        get() = state.value.password
    private val confirmPassword
        get() = state.value.confirmPassword

    fun onUsernameChange(newValue: String) {
        state.value = state.value.copy(username = newValue)
    }

    fun onPasswordChange(newValue: String) {
        state.value = state.value.copy(password = newValue)
    }

    fun onConfirmPasswordChange(newValue: String) {
        state.value = state.value.copy(confirmPassword = newValue)
    }

    fun register(onRegisterSuccess: () -> Unit) {
        // Validação dos campos
        if (username.isEmpty()) {
            state.value = state.value.copy(error = "Email is required")
            return
        }
        if (password.isEmpty()) {
            state.value = state.value.copy(error = "Password is required")
            return
        }
        if (confirmPassword.isEmpty()) {
            state.value = state.value.copy(error = "Confirm password is required")
            return
        }
        if (password != confirmPassword) {
            state.value = state.value.copy(error = "Passwords do not match")
            return
        }

        state.value = state.value.copy(isLoading = true)
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                state.value = state.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    state.value = state.value.copy(error = null)
                    onRegisterSuccess()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    state.value = when (task.exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            state.value.copy(error = "Invalid email format")
                        }

                        is FirebaseAuthWeakPasswordException -> {
                            state.value.copy(error = "Password is too weak")
                        }

                        else -> {
                            state.value.copy(error = task.exception.toString())
                        }
                    }
                }
            }
    }
}