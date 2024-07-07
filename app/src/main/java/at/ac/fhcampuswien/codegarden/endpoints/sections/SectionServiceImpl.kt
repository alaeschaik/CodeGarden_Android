package at.ac.fhcampuswien.codegarden.endpoints.sections

import android.content.Context
import android.util.Log
import android.widget.Toast
import at.ac.fhcampuswien.codegarden.endpoints.challenges.Challenge
import at.ac.fhcampuswien.codegarden.endpoints.modules.Module
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface SectionService {
    suspend fun createSection(request: CreateSectionRequest): Flow<CreateSectionResponse>
    suspend fun getAllSections(): Flow<List<Section>>
    suspend fun getSection(id: Int): Flow<Section>
    suspend fun getSectionModule(id: Int): Flow<Module>
    suspend fun getSectionChallenges(int: Int): Flow<List<Challenge>>
    suspend fun updateSection(id: Int, request: UpdateSectionRequest): Flow<Boolean>
    suspend fun deleteSection(id: Int): Flow<Boolean>
}

class SectionServiceImpl(
    private val context: Context,
    private val sectionApi: SectionApi,
    private val sharedPrefManager: SharedPrefManager
) : SectionService {
    override suspend fun createSection(request: CreateSectionRequest): Flow<CreateSectionResponse> {
        return flow {
            if (request.moduleId == -1 || request.title.isEmpty() || request.content.isEmpty() || request.xpPoints == -1) {
                return@flow
            }

            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = sectionApi.createSection(token, request)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("SectionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to create section", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getAllSections(): Flow<List<Section>> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = sectionApi.getAllSections(token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("SectionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch sections", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getSection(id: Int): Flow<Section> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = sectionApi.getSection(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("SectionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch section", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getSectionModule(id: Int): Flow<Module> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = sectionApi.getSectionModule(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("SectionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch section module", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getSectionChallenges(id: Int): Flow<List<Challenge>> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = sectionApi.getSectionChallenges(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("SectionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch section challenges", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun updateSection(id: Int, request: UpdateSectionRequest): Flow<Boolean> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = sectionApi.updateSection(id, token, request)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("SectionServiceImpl", response.errorBody().toString())
            emit(false)
        }
    }

    override suspend fun deleteSection(id: Int): Flow<Boolean> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = sectionApi.deleteSection(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("SectionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to delete section", Toast.LENGTH_LONG).show()
            emit(false)
        }
    }
}
