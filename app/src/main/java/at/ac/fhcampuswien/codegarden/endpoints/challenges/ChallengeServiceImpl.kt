package at.ac.fhcampuswien.codegarden.endpoints.challenges

import android.content.Context
import android.util.Log
import android.widget.Toast
import at.ac.fhcampuswien.codegarden.endpoints.questions.Question
import at.ac.fhcampuswien.codegarden.endpoints.sections.Section
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ChallengeService {
    suspend fun createChallenge(request: CreateChallengeRequest): Flow<CreateChallengeResponse>
    suspend fun getAllChallenges(): Flow<List<Challenge>>
    suspend fun getChallenge(id: Int): Flow<Challenge>
    suspend fun updateChallenge(id: Int, request: UpdateChallengeRequest): Flow<Boolean>
    suspend fun deleteChallenge(id: Int): Flow<Boolean>
    suspend fun getChallengeSection(id: Int): Flow<Section>
    suspend fun getChallengeQuestions(id: Int): Flow<List<Question>>
}

class ChallengeServiceImpl(
    private val context: Context,
    private val challengeApi: ChallengeApi,
    private val sharedPrefManager: SharedPrefManager
) : ChallengeService {
    override suspend fun createChallenge(request: CreateChallengeRequest): Flow<CreateChallengeResponse> {
        return flow {
            if (request.challengeType.isEmpty() || request.sectionId == -1 || request.content.isEmpty()) {
                return@flow
            }

            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = challengeApi.createChallenge(token, request)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ChallengeServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to create challenge", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getAllChallenges(): Flow<List<Challenge>> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = challengeApi.getAllChallenges(token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ChallengeServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch challenges", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getChallenge(id: Int): Flow<Challenge> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = challengeApi.getChallenge(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ChallengeServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch challenge", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun updateChallenge(id: Int, request: UpdateChallengeRequest): Flow<Boolean> {
        return flow {
            if (request.challengeType.isEmpty() || request.sectionId == -1 || request.content.isEmpty()) {
                return@flow
            }

            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = challengeApi.updateChallenge(id, token, request)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ChallengeServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to update challenge", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun deleteChallenge(id: Int): Flow<Boolean> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = challengeApi.deleteChallenge(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ChallengeServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to delete challenge", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getChallengeSection(id: Int): Flow<Section> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = challengeApi.getChallengeSection(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ChallengeServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch challenge section", Toast.LENGTH_LONG).show()
        }
    }

    override suspend fun getChallengeQuestions(id: Int): Flow<List<Question>> {
        return flow {
            val token = "Bearer ${sharedPrefManager.fetchToken()}"
            val response = challengeApi.getChallengeQuestions(id, token)

            response.body()?.let {
                emit(it)
                return@flow
            }

            Log.e("ChallengeServiceImpl", response.errorBody().toString())
            Toast.makeText(context, "Failed to fetch challenge questions", Toast.LENGTH_LONG).show()
        }
    }
}
