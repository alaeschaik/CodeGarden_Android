package at.ac.fhcampuswien.codegarden.endpoints.challenges

import at.ac.fhcampuswien.codegarden.endpoints.questions.Question
import at.ac.fhcampuswien.codegarden.endpoints.sections.Section
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ChallengeApi {

    @POST("challenges")
    suspend fun createChallenge(
        @Header("Authorization") token: String,
        @Body request: CreateChallengeRequest
    ): Response<CreateChallengeResponse>

    @GET("challenges")
    suspend fun getAllChallenges(@Header("Authorization") token: String): Response<List<Challenge>>

    @GET("challenges/{id}")
    suspend fun getChallenge(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Response<Challenge>

    @PUT("challenges/{id}")
    suspend fun updateChallenge(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body request: UpdateChallengeRequest
    ): Response<Boolean>

    @DELETE("challenges/{id}")
    suspend fun deleteChallenge(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Response<Boolean>

    @GET("challenges/{id}/section")
    suspend fun getChallengeSection(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Response<Section>

    @GET("challenges/{id}/questions")
    suspend fun getChallengeQuestions(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Response<List<Question>>
}
