package at.ac.fhcampuswien.codegarden.endpoints.comments

import android.content.Context
import android.util.Log
import android.widget.Toast
import at.ac.fhcampuswien.codegarden.endpoints.users.User
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CommentService {
    suspend fun getUser(id: Int): Flow<User?>
}

class CommentServiceImpl(
    private val context: Context,
    private val commentApi: CommentApi,
    private val sharedPrefManager: SharedPrefManager
) : CommentService {

    override suspend fun getUser(id: Int): Flow<User?> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = commentApi.getUser(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }
            Log.e("CommentServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch user", Toast.LENGTH_LONG).show()
        }
    }
}