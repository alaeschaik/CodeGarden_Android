package at.ac.fhcampuswien.codegarden.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.codegarden.endpoints.challenges.ChallengeService
import at.ac.fhcampuswien.codegarden.endpoints.questions.Question
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuestionViewModel(
    private val challengeService: ChallengeService,
    private val sharedPrefManager: SharedPrefManager,
    challengeId: Int
) : ViewModel() {

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions = _questions.asStateFlow()

    init {
        getChallengeQuestions(challengeId) { questions ->
            _questions.value = questions
        }
    }

    private fun getChallengeQuestions(id: Int, onQuestionsFetched: (questions: List<Question>) -> Unit) {
        viewModelScope.launch {
            challengeService.getChallengeQuestions(id).collect { questions ->
                onQuestionsFetched(questions)
            }
        }
    }
}
