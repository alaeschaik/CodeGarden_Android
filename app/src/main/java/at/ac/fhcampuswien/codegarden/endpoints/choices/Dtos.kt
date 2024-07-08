package at.ac.fhcampuswien.codegarden.endpoints.choices

data class CreateChoiceRequest(
    val content: String,
    val isCorrect: Boolean
)

data class CreateChoiceResponse(
    val id: Int,
    val content: String,
    val isCorrect: Boolean,
    val questionId: Int
) {
    fun toChoice(): Choice {
        return Choice(
            this.id,
            this.content,
            this.isCorrect,
            this.questionId
        )
    }
}

data class UpdateChoiceRequest(
    val content: String,
    val isCorrect: Boolean
)

data class Choice(
    var id: Int,
    var content: String,
    var isCorrect: Boolean,
    var questionId: Int
)

data class AnswerChoiceRequest(
    val answer: String,
)
