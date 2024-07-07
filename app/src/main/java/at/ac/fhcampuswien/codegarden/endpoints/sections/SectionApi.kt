package at.ac.fhcampuswien.codegarden.endpoints.sections

import at.ac.fhcampuswien.codegarden.endpoints.modules.Module
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SectionApi {

    @POST("sections")
    suspend fun createSection(
        @Header("Authorization") token: String,
        @Body request: CreateSectionRequest
    ): Response<CreateSectionResponse>

    @GET("sections")
    suspend fun getAllSections(@Header("Authorization") token: String): Response<List<Section>>

    @GET("sections/{id}")
    suspend fun getSection(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Response<Section>

    @GET("sections/{id}/module")
    suspend fun getSectionModule(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Response<Module>

//    @GET("sections/{id}/challenges")
//    suspend fun getSectionChallenges(
//        @Path("id") id: Int,
//        @Header("Authorization") token: String,
//    ): Response<List<Challenge>>

    @PUT("sections/{id}")
    suspend fun updateSection(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body request: UpdateSectionRequest
    ): Response<Boolean>

    @DELETE("sections/{id}")
    suspend fun deleteSection(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Response<Boolean>
}
