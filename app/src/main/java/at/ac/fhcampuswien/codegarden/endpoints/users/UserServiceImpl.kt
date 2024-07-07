package at.ac.fhcampuswien.codegarden.endpoints.users

import android.content.Context
import android.widget.Toast
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UserService {
    suspend fun userLogin(username: String, password: String): Boolean
    suspend fun userRegister(
        username: String,
        email: String,
        password: String,
        firstname: String,
        lastname: String
    ): Boolean

    suspend fun getUserProfile(userId: Int): User?
    suspend fun getAllUsers(): List<User>
    suspend fun resetPassword(username: String, oldPassword: String, newPassword: String): Boolean
    suspend fun updateUserProfile(
        username: String?,
        email: String?,
        firstname: String?,
        lastname: String?
    ): Flow<Boolean>
}

class UserServiceImpl(
    private val context: Context,
    private val userApi: UserApi,
    private val sharedPrefManager: SharedPrefManager
) : UserService {

    override suspend fun userLogin(username: String, password: String): Boolean {
        if (username.isEmpty() || password.isEmpty()) {
            showToast("Please enter username and password")
            return false
        }

        sharedPrefManager.saveCredentials(username, password)
        val response = userApi.userLogin(LoginRequest(username, password))

        response.body()?.let {
            sharedPrefManager.saveUserDetails(it.id, it.token, it.expiresAt)
            return true
        }

        showToast("Login failed!")
        return false
    }

    override suspend fun userRegister(
        username: String,
        email: String,
        password: String,
        firstname: String,
        lastname: String
    ): Boolean {

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty()) {
            showToast("Please fill in all fields")
            return false
        }

        val response =
            userApi.userRegister(RegisterRequest(username, email, password, firstname, lastname))

        if (response.isSuccessful) {
            showToast("Registration successful")
            return true
        }

        showToast("Registration failed")
        return false
    }

    override suspend fun getUserProfile(userId: Int): User? {
        val token = "Bearer ${sharedPrefManager.fetchToken()}"
        val response = userApi.getUserProfile(userId, token)

        response.body()?.let {
            return it
        }

        showToast(response.errorBody()?.string())
        return null
    }

    override suspend fun getAllUsers(): List<User> {
        val token = "Bearer ${sharedPrefManager.fetchToken()}"
        val response = userApi.getAllUsers(token)

        response.body()?.let {
            return it
        }

        showToast(response.errorBody()?.string())
        return emptyList()
    }

    override suspend fun resetPassword(
        username: String,
        oldPassword: String,
        newPassword: String
    ): Boolean {
        val response =
            userApi.resetPassword(ResetPasswordRequest(username, oldPassword, newPassword))

        if (response.isSuccessful) {
            showToast("Password reset successful")
            return true
        }

        showToast(response.errorBody()?.string())
        return false
    }

    override suspend fun updateUserProfile(
        username: String?,
        email: String?,
        firstname: String?,
        lastname: String?
    ): Flow<Boolean> = flow {
        try {
            val userId = sharedPrefManager.fetchUserId() ?: throw IllegalStateException("User ID not found")
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val requestBody = UpdateProfileRequest(username, email, firstname, lastname)
            val response = userApi.updateUserProfile(userId, token, requestBody)

            if (response.isSuccessful) {
                showToast("Profile updated successfully")
                emit(true)
                return@flow
            } else {
                showToast(response.errorBody()?.string())
                emit(false)
            }
        } catch (e: Exception) {
            showToast(e.message)
            emit(false)
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}