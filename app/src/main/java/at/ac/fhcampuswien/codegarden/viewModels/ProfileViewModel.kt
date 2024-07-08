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

data class Badge(val name: String, val minXP: Float, val maxXP: Float)

class ProfileViewModel(
    private val userService: UserService,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private var _username = MutableStateFlow("")
    private var _email = MutableStateFlow("")
    private var _firstname = MutableStateFlow("")
    private var _lastname = MutableStateFlow("")

    private val _userProfile = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> get() = _userProfile

    val username = _username.asStateFlow()
    val email = _email.asStateFlow()
    val firstname = _firstname.asStateFlow()
    val lastname = _lastname.asStateFlow()

    private val badges = listOf(
        Badge("Noob", 0f, 100f),
        Badge("Beginner", 100f, 500f),
        Badge("Intermediate", 500f, 1000f),
        Badge("Advanced", 1000f, 2500f),
        Badge("Expert", 2500f, 10000f)
    )

    init {
        viewModelScope.launch {
            val userId = sharedPrefManager.fetchUserId()
            if (userId != null) {
                loadUserProfile()
            }
        }
    }

    private suspend fun loadUserProfile() {
        val user = userService.getUserProfile()
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
            loadUserProfile()
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

    fun getAllBadges(): List<Badge> {
        return badges
    }
}