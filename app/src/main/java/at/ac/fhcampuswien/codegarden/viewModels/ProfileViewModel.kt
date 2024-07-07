package at.ac.fhcampuswien.codegarden.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.codegarden.endpoints.users.User
import at.ac.fhcampuswien.codegarden.endpoints.users.UserService
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userService: UserService,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private var _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private var _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private var _firstname = MutableStateFlow("")
    val firstname = _firstname.asStateFlow()

    private var _lastname = MutableStateFlow("")
    val lastname = _lastname.asStateFlow()

    private val _userProfile = MutableStateFlow<User?>(null)

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
        username: String?,
        email: String?,
        firstname: String?,
        lastname: String?
    ) {
        viewModelScope.launch {
            userService.updateUserProfile(
                if (_userProfile.value?.username == username) null else username,
                if (_userProfile.value?.email == email) null else email,
                if (_userProfile.value?.firstname == firstname) null else firstname,
                if (_userProfile.value?.lastname == lastname) null else lastname
            )
                .collect { _ ->
                }
            loadUserProfile(sharedPrefManager.fetchUserId()!!)
        }
    }

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            sharedPrefManager.clearUserDetails()
            onComplete()
        }
    }

    fun updateUsername(newUsername: String) {
        viewModelScope.launch {
            _username.value = newUsername
        }
    }

    fun updateEmail(newEmail: String) {
        viewModelScope.launch {
            _email.value = newEmail
        }
    }

    fun updateFirstname(newFirstname: String) {
        viewModelScope.launch {
            _firstname.value = newFirstname
        }
    }

    fun updateLastname(newLastname: String) {
        viewModelScope.launch {
            _lastname.value = newLastname
        }
    }
}