package at.ac.fhcampuswien.codegarden.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.codegarden.endpoints.choices.AnswerChoiceRequest
import at.ac.fhcampuswien.codegarden.endpoints.choices.Choice
import at.ac.fhcampuswien.codegarden.endpoints.choices.ChoiceService
import at.ac.fhcampuswien.codegarden.endpoints.questions.Question
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChoiceViewModel(
    private val choiceService: ChoiceService,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private val _choices = MutableStateFlow<List<Choice>>(emptyList())
    val choices = _choices.asStateFlow()

    fun answerChoice(id: Int, request: AnswerChoiceRequest, onAnswered: (Boolean) -> Unit) {
        viewModelScope.launch {
            choiceService.answerChoice(id, request).collect { success ->
                onAnswered(success)
            }
        }
    }
}
