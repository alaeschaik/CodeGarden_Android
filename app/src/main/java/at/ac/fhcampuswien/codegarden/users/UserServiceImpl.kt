package at.ac.fhcampuswien.codegarden.users

import android.content.Context
import android.widget.Toast
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager

interface UserService {
    suspend fun userLogin(username: String, password: String): Boolean
    suspend fun userRegister(
        username: String,
        email: String,
        password: String,
        firstname: String,
        lastname: String
    ): Boolean

    suspend fun getUserProfile(userId: Int)
    suspend fun getAllUsers()
    suspend fun resetPassword(username: String, oldPassword: String, newPassword: String): Boolean
    suspend fun updateUserProfile(
        username: String?,
        email: String?,
        firstname: String?,
        lastname: String?
    )
}

class UserServiceImpl(
    private val context: Context,
    private val userApi: UserApi,
    private val sharedPrefManager: SharedPrefManager
) : UserService {

    override suspend fun userLogin(username: String, password: String): Boolean {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Please enter username and password", Toast.LENGTH_LONG).show()
            return false
        }

        sharedPrefManager.saveCredentials(username, password)
        val response = userApi.userLogin(LoginRequest(username, password))

        if (response.isSuccessful) {
            response.body()?.let {
                sharedPrefManager.saveUserDetails(it.id, it.token, it.expiresAt)
                return true
            }
        }

        Toast.makeText(context, "Login failed!", Toast.LENGTH_LONG).show()
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
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_LONG).show()
            return false
        }

        val response =
            userApi.userRegister(
                RegisterRequest(
                    username,
                    email,
                    password,
                    firstname,
                    lastname
                )
            )

        if (response.isSuccessful) {
            Toast.makeText(context, "Registration successful", Toast.LENGTH_LONG).show()
            return true
        } else {
            Toast.makeText(context, "Registration failed", Toast.LENGTH_LONG).show()
            return false
        }
    }

    override suspend fun getUserProfile(userId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers() {
        val token = "Bearer ${sharedPrefManager.fetchToken()}"
        val response = userApi.getAllUsers(token)

        if (response.isSuccessful && response.body() != null) {
            // Handle successful response
        } else {
            // Handle error
            val errorMessage = response.errorBody()?.string()
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun resetPassword(
        username: String,
        oldPassword: String,
        newPassword: String
    ): Boolean {
        //val token = "Bearer ${sharedPrefManager.fetchToken()}"
        val response =
            userApi.resetPassword(ResetPasswordRequest(username, oldPassword, newPassword))

        if (response.isSuccessful) {
            Toast.makeText(context, "Password reset successful", Toast.LENGTH_LONG).show()
            return true
        }

        Toast.makeText(context, response.errorBody()?.string(), Toast.LENGTH_LONG).show()
        return false
    }

    override suspend fun updateUserProfile(
        username: String?,
        email: String?,
        firstname: String?,
        lastname: String?
    ) {
        TODO("Not yet implemented")
    }
}

