package at.ac.fhcampuswien.codegarden.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.codegarden.endpoints.users.UserService
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class LoginViewModel(
    private val userService: UserService,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    fun login(emailOrUsername: String, password: String, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            val success = userService.userLogin(emailOrUsername, password)
            if (success) {
                // Navigate to the home screen
                onLoginSuccess()
            }
        }
    }

    fun isUserLoggedIn(): Boolean {
        val token = sharedPrefManager.fetchToken()
        val tokenExpirationDate = sharedPrefManager.fetchTokenExpiration()
        if (token != null && tokenExpirationDate != null) {
            if (tokenExpirationDate.after(Date.from(Instant.now()))) {
                return true
            }
        }
        return false
    }
}