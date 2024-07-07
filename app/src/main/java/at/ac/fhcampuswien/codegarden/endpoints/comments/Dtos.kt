package at.ac.fhcampuswien.codegarden.endpoints.comments

import at.ac.fhcampuswien.codegarden.endpoints.posts.Comment

data class CreateCommentRequest(
    val postId: Int,
    val userId: Int,
    val content: String
)

data class CreateCommentResponse(
    val id: Int,
    val postId: Int,
    val userId: Int,
    val content: String,
    val createdAt: String
)
{
    fun toComment(): Comment {
        return Comment(id, postId, userId, content, createdAt)
    }
}