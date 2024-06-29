package at.ac.fhcampuswien.codegarden.endpoints.comments

import at.ac.fhcampuswien.codegarden.endpoints.users.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CommentApi {

    @GET("comments/{id}/user")
    suspend fun getUser(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<User?>
}