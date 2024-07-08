package at.ac.fhcampuswien.codegarden.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.codegarden.endpoints.choices.AnswerChoiceRequest
import at.ac.fhcampuswien.codegarden.endpoints.choices.Choice
import at.ac.fhcampuswien.codegarden.endpoints.choices.ChoiceService
import at.ac.fhcampuswien.codegarden.endpoints.questions.Question
import at.ac.fhcampuswien.codegarden.endpoints.questions.QuestionService
import at.ac.fhcampuswien.codegarden.endpoints.users.UpdateUserXpPointsRequest
import at.ac.fhcampuswien.codegarden.endpoints.users.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChoiceViewModel(
    private val userService: UserService,
    private val choiceService: ChoiceService,
    private val questionService: QuestionService,
    questionId: Int
) : ViewModel() {

    private val _choices = MutableStateFlow<List<Choice>>(emptyList())
    val choices = _choices.asStateFlow()

    init {
        getQuestionChoices(questionId) { choices ->
            _choices.value = choices
        }
    }

    private fun getQuestionChoices(id: Int, onChoicesFetched: (choices: List<Choice>) -> Unit) {
        viewModelScope.launch {
            questionService.getQuestionChoices(id).collect { choices ->
                onChoicesFetched(choices)
            }
        }
    }

    fun answerChoice(id: Int, request: AnswerChoiceRequest, onAnswered: (Boolean) -> Unit) {
        viewModelScope.launch {
            choiceService.answerChoice(id, request).collect { success ->
                onAnswered(success)
            }
        }
    }

    fun updateUserXpPoints(xpPoints: Float, onUpdated: (Boolean) -> Unit) {
        viewModelScope.launch {
            val request = UpdateUserXpPointsRequest(xpPoints)
            userService.updateUserXpPoints(request).collect { success ->
                onUpdated(success)
            }
        }
    }

    fun getQuestion(id: Int, onQuestionFetched: (Question) -> Unit) {
        viewModelScope.launch {
            questionService.getQuestion(id).collect { question ->
                onQuestionFetched(question)
            }
        }
    }
}
