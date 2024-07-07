package at.ac.fhcampuswien.codegarden.endpoints.discussions

import android.content.Context
import android.util.Log
import android.widget.Toast
import at.ac.fhcampuswien.codegarden.endpoints.contributions.Contribution
import at.ac.fhcampuswien.codegarden.endpoints.users.User
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface DiscussionService {
    suspend fun createDiscussion(request: CreateDiscussionRequest): Flow<CreateDiscussionResponse?>
    suspend fun getAllDiscussions(): Flow<List<Discussion>>
    suspend fun getDiscussion(id: Int): Flow<List<Discussion>>
    suspend fun getContributions(id: Int): Flow<List<Contribution>>
    suspend fun getUserForDiscussion(id: Int): Flow<User?>
    suspend fun updateDiscussion(id: Int, request: UpdateDiscussionRequest): Flow<Boolean>
    suspend fun deleteDiscussion(id: Int): Flow<Boolean>
}

class DiscussionServiceImpl(
    private val context: Context,
    private val discussionApi: DiscussionApi,
    private val sharedPrefManager: SharedPrefManager
) : DiscussionService {
    override suspend fun createDiscussion(request: CreateDiscussionRequest): Flow<CreateDiscussionResponse?> {
        return flow {
            if (request.title.isEmpty() || request.content.isEmpty() || request.userId == -1) {
                emit(null)
                Toast.makeText(context, "Failed to create discussion", Toast.LENGTH_LONG).show()
                return@flow
            }

            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = discussionApi.createDiscussion(token, request)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("DiscussionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to create discussion", Toast.LENGTH_LONG).show()
            emit(null)
        }
    }

    override suspend fun getAllDiscussions(): Flow<List<Discussion>> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = discussionApi.getAllDiscussions(token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("DiscussionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch discussions", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getDiscussion(id: Int): Flow<List<Discussion>> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = discussionApi.getDiscussion(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("DiscussionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch discussion", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getContributions(id: Int): Flow<List<Contribution>> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = discussionApi.getContributions(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("DiscussionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch contributions", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getUserForDiscussion(id: Int): Flow<User?> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = discussionApi.getUser(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("DiscussionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch user", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun updateDiscussion(
        id: Int,
        request: UpdateDiscussionRequest
    ): Flow<Boolean> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = discussionApi.updateDiscussion(id, token, request)

            if (response.isSuccessful) {
                emit(true)
                return@flow
            }

            Log.e("DiscussionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to update discussion", Toast.LENGTH_LONG).show()
            emit(false)
        }
    }

    override suspend fun deleteDiscussion(id: Int): Flow<Boolean> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = discussionApi.deleteDiscussion(id, token)

            if (response.isSuccessful) {
                emit(true)
                return@flow
            }

            Log.e("DiscussionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to delete discussion", Toast.LENGTH_LONG).show()
            emit(false)
        }
    }
}