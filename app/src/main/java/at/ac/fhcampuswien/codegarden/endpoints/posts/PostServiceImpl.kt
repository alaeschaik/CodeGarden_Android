package at.ac.fhcampuswien.codegarden.endpoints.posts

import android.content.Context
import android.util.Log
import android.widget.Toast
import at.ac.fhcampuswien.codegarden.endpoints.users.User
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface PostService {
    suspend fun createPost(request: CreatePostRequest): Flow<CreatePostResponse?>
    suspend fun getAllPosts(): Flow<List<Post>>
    suspend fun getComments(id: Int): Flow<List<Comment>>
    suspend fun getUser(id: Int): Flow<User?>
    suspend fun updatePost(id: Int, request: UpdatePostRequest): Flow<Boolean>
}

class PostServiceImpl(
    private val context: Context,
    private val postApi: PostApi,
    private val sharedPrefManager: SharedPrefManager
) : PostService {
    override suspend fun createPost(request: CreatePostRequest): Flow<CreatePostResponse?> {
        return flow {
            if (request.title.isEmpty() || request.content.isEmpty() || request.userId == -1) {
                Toast.makeText(context, "Failed to create a post", Toast.LENGTH_LONG).show()
                return@flow
            }

            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = postApi.createPost(token, request)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("PostServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to create post", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getAllPosts(): Flow<List<Post>> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = postApi.getAllPosts(token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("PostServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch posts", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getComments(id: Int): Flow<List<Comment>> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = postApi.getComments(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("PostServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch comments", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getUser(id: Int): Flow<User?> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = postApi.getUser(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }
            Log.e("PostServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch user", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun updatePost(id: Int, request: UpdatePostRequest): Flow<Boolean> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = postApi.updatePost(id, token, request)

            if (response.isSuccessful) {
                emit(true)
                return@flow
            }

            Log.e("PostServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to update post", Toast.LENGTH_LONG).show()
            emit(false)
        }
    }
}