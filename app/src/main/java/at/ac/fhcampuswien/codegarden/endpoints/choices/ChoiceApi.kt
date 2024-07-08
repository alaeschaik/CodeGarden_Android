package at.ac.fhcampuswien.codegarden.endpoints.choices

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ChoiceApi {

    @POST("choices")
    suspend fun createChoice(
        @Header("Authorization") token: String,
        @Body request: CreateChoiceRequest
    ): Response<CreateChoiceResponse>

    @GET("choices")
    suspend fun getAllChoices(@Header("Authorization") token: String): Response<List<Choice>>

    @GET("choices/{id}")
    suspend fun getChoice(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<Choice>

    @PUT("choices/{id}")
    suspend fun updateChoice(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body request: UpdateChoiceRequest
    ): Response<Boolean>

    @DELETE("choices/{id}")
    suspend fun deleteChoice(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<Boolean>

    @POST("choices/{id}/answer")
    suspend fun answerChoice(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body request: AnswerChoiceRequest
    ): Response<Boolean>
}
