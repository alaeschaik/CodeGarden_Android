package at.ac.fhcampuswien.codegarden.endpoints.questions

import at.ac.fhcampuswien.codegarden.endpoints.choices.Choice
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface QuestionApi {

    @POST("questions")
    suspend fun createQuestion(
        @Header("Authorization") token: String,
        @Body request: CreateQuestionRequest
    ): Response<CreateQuestionResponse>

    @GET("questions")
    suspend fun getAllQuestions(@Header("Authorization") token: String): Response<List<Question>>

    @GET("questions/{id}")
    suspend fun getQuestion(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Response<Question>

    @PUT("questions/{id}")
    suspend fun updateQuestion(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body request: UpdateQuestionRequest
    ): Response<Boolean>

    @DELETE("questions/{id}")
    suspend fun deleteQuestion(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Response<Boolean>

    @GET("questions/{id}/choices")
    suspend fun getQuestionChoices(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<List<Choice>>

    @POST("questions/{id}/answer")
    suspend fun answerQuestion(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body request: AnswerQuestionRequest
    ): Response<Unit>
}