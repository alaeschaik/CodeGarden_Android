package at.ac.fhcampuswien.codegarden.endpoints.users

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @POST("users/login")
    suspend fun userLogin(@Body request: LoginRequest): Response<LoginResponse>

    @POST("users/register")
    suspend fun userRegister(@Body request: RegisterRequest): Response<RegisterResponse>

    @GET("users")
    suspend fun getAllUsers(@Header("Authorization") token: String): Response<List<User>>

    @POST("users/reset-password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<Unit>

    @GET("users/{id}")
    suspend fun getUserProfile(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<User?>

    @PUT("users/{id}]")
    suspend fun updateUserProfile(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body request: UpdateProfileRequest
    ): Response<Unit>
}

