package at.ac.fhcampuswien.codegarden.endpoints.posts

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf

data class CreatePostRequest(
    val userId: Int,
    val title: String,
    val content: String
)


data class CreatePostResponse(
    val id: Int,
    val userId: Int,
    val title: String,
    val content: String,
    val upvotes: Int,
    val downvotes: Int,
    val createdAt: String
) {
    fun toPost(): Post {
        return Post(
            this.id,
            this.userId,
            this.title,
            this.content,
            mutableIntStateOf(this.upvotes),
            mutableIntStateOf(this.downvotes),
            this.createdAt
        )
    }
}

data class UpdatePostRequest(
    val title: String,
    val content: String,
    val upvotes: Int,
    val downvotes: Int
)

data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    var content: String,
    var upvotes: MutableIntState,
    var downvotes: MutableIntState,
    val createdAt: String
)

data class Comment(
    val id: Int,
    val postId: Int,
    val userId: Int,
    val content: String,
    val createdAt: String
)