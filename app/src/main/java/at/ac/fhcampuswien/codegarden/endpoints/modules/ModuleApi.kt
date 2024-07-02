package at.ac.fhcampuswien.codegarden.endpoints.modules

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ModuleApi {

    @POST("modules")
    suspend fun createModule(
        @Header("Authorization") token: String,
        @Body request: CreateModuleRequest
    ): Response<CreateModuleResponse>

    @GET("modules")
    suspend fun getAllModules(@Header("Authorization") token: String): Response<List<Module>>

    @GET("modules/{id}")
    suspend fun getModule(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Response<Module>

    @PUT("modules/{id}")
    suspend fun updateModule(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body request: UpdateModuleRequest
    ): Response<Boolean>

    @DELETE("modules/{id}")
    suspend fun deleteModule(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Response<Boolean>

    @GET("modules/{id}/sections")
    suspend fun getModuleSections(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Response<List<Section>>
}
