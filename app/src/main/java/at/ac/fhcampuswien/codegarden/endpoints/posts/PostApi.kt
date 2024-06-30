package at.ac.fhcampuswien.codegarden.endpoints.posts

import at.ac.fhcampuswien.codegarden.endpoints.users.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PostApi {

    @POST("posts")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Body request: CreatePostRequest
    ): Response<CreatePostResponse?>

    @GET("posts")
    suspend fun getAllPosts(@Header("Authorization") token: String): Response<List<Post>>

    @GET("posts/{id}/comments")
    suspend fun getComments(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<List<Comment>>

    @GET("posts/{id}/user")
    suspend fun getUser(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<User?>

    @PUT("posts/{id}")
    suspend fun updatePost(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body request: UpdatePostRequest
    ): Response<Boolean>
}