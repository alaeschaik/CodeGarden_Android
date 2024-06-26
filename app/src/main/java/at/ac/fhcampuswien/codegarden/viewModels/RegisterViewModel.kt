package at.ac.fhcampuswien.codegarden.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.codegarden.endpoints.users.UserService
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userService: UserService
) : ViewModel(
) {
    fun register(
        username: String,
        email: String,
        password: String,
        firstname: String,
        lastname: String,
        onRegisterSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val success = userService.userRegister(username, email, password, firstname, lastname)
            if (success) {
                // Navigate to the home screen
                onRegisterSuccess()
            }
        }
    }
}