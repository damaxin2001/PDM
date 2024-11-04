package ipca.pdm.myshoppinglist

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class LoginState(
    val isLoading: Boolean = false,
    val error: String? = null
)

class LoginViewModel  : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun login(username: String, password: String, onLoginSuccess: () -> Unit) {
        _state.value = _state.value.copy(isLoading = true)
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword( username, password)
            .addOnCompleteListener { task ->
                _state.value = _state.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    _state.value = _state.value.copy(error = null)
                    onLoginSuccess()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    _state.value = _state.value.copy(error = task.exception.toString())
                }
            }
    }

}