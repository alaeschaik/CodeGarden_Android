package at.ac.fhcampuswien.codegarden.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.codegarden.endpoints.challenges.Challenge
import at.ac.fhcampuswien.codegarden.endpoints.challenges.ChallengeService
import at.ac.fhcampuswien.codegarden.endpoints.questions.Question
import at.ac.fhcampuswien.codegarden.endpoints.sections.SectionService
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChallengeViewModel(
    private val sectionService: SectionService,
    private val challengeService: ChallengeService,
    private val sharedPrefManager: SharedPrefManager,
    sectionId: Int
) : ViewModel() {

    private val _challenges = MutableStateFlow<List<Challenge>>(emptyList())
    val challenges = _challenges.asStateFlow()

    init {
        getSectionChallenges(sectionId) { challenges ->
            _challenges.value = challenges
        }
    }

    private fun getSectionChallenges(id: Int, onChallengesFetched: (challenges: List<Challenge>) -> Unit) {
        viewModelScope.launch {
            sectionService.getSectionChallenges(id).collect { challenges ->
                onChallengesFetched(challenges)
            }
        }
    }

    fun getChallengeQuestions(id: Int, onQuestionsFetched: (questions: List<Question>) -> Unit) {
        viewModelScope.launch {
            challengeService.getChallengeQuestions(id).collect { questions ->
                onQuestionsFetched(questions)
            }
        }
    }
}
