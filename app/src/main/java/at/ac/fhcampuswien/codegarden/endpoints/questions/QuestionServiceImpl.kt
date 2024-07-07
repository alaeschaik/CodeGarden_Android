package at.ac.fhcampuswien.codegarden.endpoints.questions

import android.content.Context
import android.util.Log
import android.widget.Toast
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface QuestionService {
    suspend fun createQuestion(request: CreateQuestionRequest): Flow<CreateQuestionResponse>
    suspend fun getAllQuestions(): Flow<List<Question>>
    suspend fun getQuestion(id: Int): Flow<Question>
    suspend fun updateQuestion(id: Int, request: UpdateQuestionRequest): Flow<Boolean>
    suspend fun deleteQuestion(id: Int): Flow<Boolean>
    suspend fun answerQuestion(id: Int, request: AnswerQuestionRequest): Flow<Boolean>
}

class QuestionServiceImpl(
    private val context: Context,
    private val questionApi: QuestionApi,
    private val sharedPrefManager: SharedPrefManager
) : QuestionService {
    override suspend fun createQuestion(request: CreateQuestionRequest): Flow<CreateQuestionResponse> {
        return flow {
            if (request.content.isEmpty() || request.correctAnswer.isEmpty()) {
                return@flow
            }

            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = questionApi.createQuestion(token, request)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("QuestionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to create question", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getAllQuestions(): Flow<List<Question>> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = questionApi.getAllQuestions(token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("QuestionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch questions", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getQuestion(id: Int): Flow<Question> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = questionApi.getQuestion(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("QuestionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch question", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun updateQuestion(id: Int, request: UpdateQuestionRequest): Flow<Boolean> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = questionApi.updateQuestion(id, token, request)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("QuestionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to update question", Toast.LENGTH_LONG).show()
            emit(false)
        }
    }

    override suspend fun deleteQuestion(id: Int): Flow<Boolean> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = questionApi.deleteQuestion(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("QuestionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to delete question", Toast.LENGTH_LONG).show()
            emit(false)
        }
    }

    override suspend fun answerQuestion(id: Int, request: AnswerQuestionRequest): Flow<Boolean> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = questionApi.answerQuestion(id, token, request)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("QuestionServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to answer question", Toast.LENGTH_LONG).show()
            emit(false)
        }
    }
}
