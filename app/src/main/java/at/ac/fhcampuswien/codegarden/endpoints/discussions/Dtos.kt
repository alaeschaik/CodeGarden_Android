package at.ac.fhcampuswien.codegarden.endpoints.discussions

import at.ac.fhcampuswien.codegarden.endpoints.contributions.Contribution

data class Discussion(
    val id: Int,
    val title: String,
    var content: String,
    val userId: Int,
    val createdAt: String,
    val contributions: List<Contribution>
)

data class CreateDiscussionRequest(
    val title: String,
    val content: String,
    val userId: Int
)

data class CreateDiscussionResponse(
    val id: Int,
    val title: String,
    val content: String,
    val userId: Int,
    val createdAt: String,
    val contributions: List<Contribution>
)
{
    fun toDiscussion(): Discussion {
        return Discussion(id, title, content, userId, createdAt, contributions)
    }
}

data class UpdateDiscussionRequest(
    val title: String,
    val content: String
)