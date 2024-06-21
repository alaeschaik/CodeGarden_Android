package at.ac.fhcampuswien.codegarden.users

data class LoginRequest(
    val usernameOrEmail: String,
    val password: String
)

data class LoginResponse(
    val id: Int,
    val token: String,
    val expiresAt: String,
    val username: String
)

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val xpPoints: Float,
    val createdAt: String
)

data class ResetPasswordRequest(
    val usernameOrEmail: String,
    val oldPassword: String,
    val newPassword: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val firstname: String,
    val lastname: String
)

data class RegisterResponse(
    val id: Int,
    val username: String,
    val email: String,
    val firstname: String,
    val lastname: String,
    val xpPoints: Float,
    val createdAt: String
)