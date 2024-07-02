package at.ac.fhcampuswien.codegarden.endpoints.comments

import at.ac.fhcampuswien.codegarden.endpoints.posts.Comment
import at.ac.fhcampuswien.codegarden.endpoints.users.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentApi {

    @GET("comments/{id}/user")
    suspend fun getUser(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<User?>

    @POST("comments")
    suspend fun createComment(
        @Header("Authorization") token: String,
        @Body requestBody: CreateCommentRequest
    ): Response<CreateCommentResponse>

    @DELETE("comments/{id}")
    suspend fun deleteComment(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Unit>
}