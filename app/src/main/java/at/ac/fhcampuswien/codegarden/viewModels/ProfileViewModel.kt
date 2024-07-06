package at.ac.fhcampuswien.codegarden.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.codegarden.endpoints.users.User
import at.ac.fhcampuswien.codegarden.endpoints.users.UserService
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userService: UserService,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private var _username = MutableStateFlow("")
    var username = _username.asStateFlow()

    private var _email = MutableStateFlow("")
    var email = _email.asStateFlow()

    private var _firstname = MutableStateFlow("")
    var firstname = _firstname.asStateFlow()

    private var _lastname = MutableStateFlow("")
    var lastname = _lastname.asStateFlow()

    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile: StateFlow<User?> = _userProfile

    private val _updateResult = MutableStateFlow<Boolean?>(null)
    val updateResult: StateFlow<Boolean?> = _updateResult

    init {
        viewModelScope.launch {
            val userId = sharedPrefManager.fetchUserId()
            if (userId != null) {
                loadUserProfile(userId)
            }
        }
    }

    private suspend fun loadUserProfile(userId: Int) {
        val user = userService.getUserProfile(userId)
        user?.let {
            _username.value = it.username
            _email.value = it.email
            _firstname.value = it.firstname
            _lastname.value = it.lastname
            _userProfile.value = it
        }
    }

    fun updateProfile(
        username: String,
        email: String,
        firstname: String,
        lastname: String
    ) {
        viewModelScope.launch {
            userService.updateUserProfile(username, email, firstname, lastname)
                .collect { result ->
                    if (result) {
                        // Update local state immediately after a successful update
                        _username.value = username
                        _email.value = email
                        _firstname.value = firstname
                        _lastname.value = lastname
                    }
                }
        }
    }

    fun reloadUserProfile() {
        viewModelScope.launch {
            val userId = sharedPrefManager.fetchUserId()
            if (userId != null) {
                loadUserProfile(userId)
            }
        }
    }

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            sharedPrefManager.clearUserDetails()
            onComplete()
        }
    }
}