package at.ac.fhcampuswien.codegarden.endpoints.questions

data class CreateQuestionRequest(
    val content: String,
    val correctAnswer: String,
    val type: String,
    val xpPoints: Float
)

data class CreateQuestionResponse(
    val id: Int,
    val content: String,
    val correctAnswer: String,
    val challengeId: Int,
    val type: String,
    val xpPoints: Float
) {
    fun toQuestion(): Question {
        return Question(
            this.id,
            this.content,
            this.correctAnswer,
            this.challengeId,
            this.type,
            this.xpPoints
        )
    }
}

data class UpdateQuestionRequest(
    val content: String,
    val correctAnswer: String,
    val type: String,
    val xpPoints: Float
)

data class Question(
    val id: Int,
    val content: String,
    val correctAnswer: String,
    val challengeId: Int,
    val type: String,
    val xpPoints: Float
)

data class AnswerQuestionRequest(
    val answer: String,
)
