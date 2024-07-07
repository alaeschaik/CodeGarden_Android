package at.ac.fhcampuswien.codegarden.endpoints.discussions

import androidx.room.Delete
import at.ac.fhcampuswien.codegarden.endpoints.contributions.Contribution
import at.ac.fhcampuswien.codegarden.endpoints.users.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DiscussionApi {
    @POST("discussions")
    suspend fun createDiscussion(
        @Header("Authorization") token: String,
        @Body request: CreateDiscussionRequest
    ): Response<CreateDiscussionResponse?>

    @GET("discussions")
    suspend fun getAllDiscussions(@Header("Authorization") token: String): Response<List<Discussion>>

    @GET("discussions/{id}/")
    suspend fun getDiscussion(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<List<Discussion>>

    @GET("discussions/{id}/contributions")
    suspend fun getContributions(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<List<Contribution>>

    @PUT("discussions/{id}")
    suspend fun updateDiscussion(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body request: UpdateDiscussionRequest
    ): Response<Boolean>

    @GET("discussions/{id}/user")
    suspend fun getUser(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<User?>

    @DELETE("discussions/{id}")
    suspend fun deleteDiscussion(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<Boolean>
}