package at.ac.fhcampuswien.codegarden.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.codegarden.users.UserService
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val userService: UserService
) : ViewModel() {
    fun resetPassword(
        username: String,
        oldPassword: String,
        newPassword: String,
        onResetSuccess: () -> Unit
    ) {
        // Call the reset password method from the UserService
        // If the reset was successful, call onResetSuccess
        viewModelScope.launch {
            val success = userService.resetPassword(username, oldPassword, newPassword)
            if (success) {
                onResetSuccess()
            }
        }
    }
}