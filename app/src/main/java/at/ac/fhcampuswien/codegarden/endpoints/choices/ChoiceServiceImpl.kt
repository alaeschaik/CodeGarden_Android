package at.ac.fhcampuswien.codegarden.endpoints.choices

import android.content.Context
import android.util.Log
import android.widget.Toast
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow

interface ChoiceService {
    suspend fun createChoice(request: CreateChoiceRequest): Flow<CreateChoiceResponse>
    suspend fun getAllChoices(): Flow<List<Choice>>
    suspend fun getChoice(id: Int): Flow<Choice>
    suspend fun updateChoice(id: Int, request: UpdateChoiceRequest): Flow<Boolean>
    suspend fun deleteChoice(id: Int): Flow<Boolean>
    suspend fun answerChoice(id: Int, request: AnswerChoiceRequest): Flow<Boolean>
}

class ChoiceServiceImpl(
    private val context: Context,
    private val choiceApi: ChoiceApi,
    private val sharedPrefManager: SharedPrefManager
) : ChoiceService {
    override suspend fun createChoice(request: CreateChoiceRequest): Flow<CreateChoiceResponse> {
        return flow {
            if (request.content.isEmpty()) {
                return@flow
            }

            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = choiceApi.createChoice(token, request)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ChoiceServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to create choice", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getAllChoices(): Flow<List<Choice>> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = choiceApi.getAllChoices(token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ChoiceServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch choices", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getChoice(id: Int): Flow<Choice> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = choiceApi.getChoice(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ChoiceServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch choice", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun updateChoice(id: Int, request: UpdateChoiceRequest): Flow<Boolean> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = choiceApi.updateChoice(id, token, request)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ChoiceServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to update choice", Toast.LENGTH_LONG).show()
            emit(false)
        }
    }

    override suspend fun deleteChoice(id: Int): Flow<Boolean> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = choiceApi.deleteChoice(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ChoiceServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to delete choice", Toast.LENGTH_LONG).show()
            emit(false)
        }
    }

    override suspend fun answerChoice(id: Int, request: AnswerChoiceRequest): Flow<Boolean> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = choiceApi.answerChoice(id, token, request)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ChoiceServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to answer choice", Toast.LENGTH_LONG).show()
            emit(false)
        }
    }
}
