package at.ac.fhcampuswien.codegarden.endpoints.questions

data class CreateQuestionRequest(
    val content: String,
    val correctAnswer: String
)

data class CreateQuestionResponse(
    val id: Int,
    val content: String,
    val correctAnswer: String,
    val challengeId: Int
) {
    fun toQuestion(): Question {
        return Question(
            this.id,
            this.content,
            this.correctAnswer,
            this.challengeId
        )
    }
}

data class UpdateQuestionRequest(
    val content: String,
    val correctAnswer: String
)

data class Question(
    val id: Int,
    val content: String,
    val correctAnswer: String,
    val challengeId: Int
)

data class AnswerQuestionRequest(
    val content: String,
    val correctAnswer: String
)