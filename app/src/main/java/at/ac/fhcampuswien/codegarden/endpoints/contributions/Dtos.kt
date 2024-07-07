package at.ac.fhcampuswien.codegarden.endpoints.contributions

data class Contribution(
    val id: String,
    val discussionId: Int,
    val userId: Int,
    val content: String,
    val createdAt: String,
    val contributions: List<Contribution>
)
