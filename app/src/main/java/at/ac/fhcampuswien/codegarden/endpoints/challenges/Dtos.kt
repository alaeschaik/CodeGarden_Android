package at.ac.fhcampuswien.codegarden.endpoints.challenges

data class CreateChallengeRequest(
    val challengeType: String,
    val sectionId: Int,
    val content: String,
    val xpPoints: Float
)

data class CreateChallengeResponse(
    val id: Int,
    val challengeType: String,
    val sectionId: Int,
    val content: String,
    val xpPoints: Float
) {
    fun toChallenge(): Challenge {
        return Challenge(
            this.id,
            this.challengeType,
            this.sectionId,
            this.content,
            this.xpPoints
        )
    }
}

data class UpdateChallengeRequest(
    val challengeType: String,
    val sectionId: Int,
    val content: String,
    val xpPoints: Float
)

data class Challenge(
    val id: Int,
    val challengeType: String,
    val sectionId: Int,
    val content: String,
    val xpPoints: Float
)
