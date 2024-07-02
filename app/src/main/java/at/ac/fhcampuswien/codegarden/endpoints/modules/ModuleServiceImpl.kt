package at.ac.fhcampuswien.codegarden.endpoints.modules

import android.content.Context
import android.util.Log
import android.widget.Toast
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ModuleService {
    suspend fun createModule(request: CreateModuleRequest): Flow<CreateModuleResponse?>
    suspend fun getAllModules(): Flow<List<Module>>
    suspend fun getModule(id: Int): Flow<Module>
    suspend fun updateModule(id: Int, request: UpdateModuleRequest): Flow<Boolean>
    suspend fun deleteModule(id: Int): Flow<Boolean>
    suspend fun getModuleSections(id: Int): Flow<List<Section>>
}

class ModuleServiceImpl(
    private val context: Context,
    private val moduleApi: ModuleApi,
    private val sharedPrefManager: SharedPrefManager
) : ModuleService {
    override suspend fun createModule(request: CreateModuleRequest): Flow<CreateModuleResponse?> {
        return flow {
            if (request.title.isEmpty() || request.description.isEmpty() || request.introduction.isEmpty() || request.content.isEmpty() || request.totalXpPoints == -1) {
                return@flow
            }

            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = moduleApi.createModule(token, request)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ModuleServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to create module", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getAllModules(): Flow<List<Module>> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = moduleApi.getAllModules(token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ModuleServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch modules", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getModule(id: Int): Flow<Module> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = moduleApi.getModule(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ModuleServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch module", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun updateModule(id: Int, request: UpdateModuleRequest): Flow<Boolean> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = moduleApi.updateModule(id, token, request)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ModuleServiceImpl", response.errorBody().toString())
            emit(false)
        }
    }

    override suspend fun deleteModule(id: Int): Flow<Boolean> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = moduleApi.deleteModule(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ModuleServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to delete module", Toast.LENGTH_LONG).show()
            emit(false)
        }
    }

    override suspend fun getModuleSections(id: Int): Flow<List<Section>> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = moduleApi.getModuleSections(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ModuleServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch module sections", Toast.LENGTH_LONG).show()
        }
    }
}
