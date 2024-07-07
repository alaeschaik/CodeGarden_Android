package at.ac.fhcampuswien.codegarden.endpoints.challenges

data class CreateChallengeRequest(
    val challengeType: String,
    val sectionId: Int,
    val content: String
)

data class CreateChallengeResponse(
    val id: Int,
    val challengeType: String,
    val sectionId: Int,
    val content: String
) {
    fun toChallenge(): Challenge {
        return Challenge(
            this.id,
            this.challengeType,
            this.sectionId,
            this.content
        )
    }
}

data class UpdateChallengeRequest(
    val challengeType: String,
    val sectionId: Int,
    val content: String
)

data class Challenge(
    val id: Int,
    val challengeType: String,
    val sectionId: Int,
    val content: String
)
