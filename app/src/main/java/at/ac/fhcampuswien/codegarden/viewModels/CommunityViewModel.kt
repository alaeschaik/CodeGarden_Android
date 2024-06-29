package at.ac.fhcampuswien.codegarden.viewModels

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.codegarden.endpoints.comments.CommentService
import at.ac.fhcampuswien.codegarden.endpoints.posts.Comment
import at.ac.fhcampuswien.codegarden.endpoints.posts.CreatePostRequest
import at.ac.fhcampuswien.codegarden.endpoints.posts.Post
import at.ac.fhcampuswien.codegarden.endpoints.posts.PostService
import at.ac.fhcampuswien.codegarden.endpoints.posts.UpdatePostRequest
import at.ac.fhcampuswien.codegarden.endpoints.users.User
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommunityViewModel(
    private val postService: PostService,
    private val commentService: CommentService,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts = _posts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)

    init {
        getAllPosts()
    }

    fun createPost(
        title: String,
        content: String,
        onPostSuccess: (post: Post) -> Unit
    ) {
        // Call the create post method from the PostService
        // If the post was successful, call onPostSuccess
        viewModelScope.launch {
            val userId = sharedPrefManager.fetchUserId() ?: -1
            val requestBody = CreatePostRequest(userId, title, content)
            postService.createPost(requestBody).collect { response ->
                if (response != null) {
                    onPostSuccess(response.toPost())
                }
            }
        }
    }

    private fun getAllPosts() {
        // Call the get all posts method from the PostService
        // If the posts were fetched successfully, call onPostsFetched
        viewModelScope.launch {
            _isLoading.value = true
            postService.getAllPosts().collect { posts ->
                posts.let {
                    _posts.value = posts
                }
            }
            _isLoading.value = false
        }
    }

    private fun getComments(
        postId: Int,
        onCommentsFetched: (comments: List<Comment>) -> Unit
    ) {
        // Call the get comments method from the PostService
        // If the comments were fetched successfully, call onCommentsFetched
        viewModelScope.launch {
            postService.getComments(postId).collect { comments ->
                onCommentsFetched(comments)
            }
        }
    }

    private fun getUserForPost(
        postId: Int,
        onUserFetched: (user: User) -> Unit
    ) {
        // Call the get user method from the PostService
        // If the user was fetched successfully, call onUserFetched
        viewModelScope.launch {
            postService.getUser(postId).collect { user ->
                if (user != null) {
                    onUserFetched(user)
                }
            }
        }
    }

    private fun getUserForComment(
        commentId: Int,
        onUserFetched: (user: User) -> Unit
    ) {
        // Call the get user method from the CommentService
        // If the user was fetched successfully, call onUserFetched
        viewModelScope.launch {
            commentService.getUser(commentId).collect { user ->
                if (user != null) {
                    onUserFetched(user)
                }
            }
        }
    }

    fun updatePost(post: Post) {
        viewModelScope.launch {
            postService.updatePost(
                post.id,
                UpdatePostRequest(
                    post.title,
                    post.content,
                    post.upvotes.intValue,
                    post.downvotes.intValue
                )
            ).collect { success ->
                if (success) {
                    _posts.value = _posts.value.map { if (it.id == post.id) post else it }.toList()
                }
            }
        }
    }

    @Composable
    fun getCommentsForPost(postId: Int): List<Comment> {
        var comments by remember { mutableStateOf(emptyList<Comment>()) }

        LaunchedEffect(key1 = postId) {
            getComments(postId) { c ->
                comments = c
                Log.d("Comments", comments.toString())
            }
        }
        return comments
    }

    @Composable
    fun getUserNameForPost(postId: Int): String {
        var username by remember { mutableStateOf("") }

        LaunchedEffect(key1 = postId) {
            getUserForPost(postId) { user ->
                username = user.username
            }
        }
        return username
    }

    @Composable
    fun getUserNameForComment(commentId: Int): String {
        var username by remember { mutableStateOf("") }

        LaunchedEffect(key1 = commentId) {
            getUserForComment(commentId) { user ->
                username = user.username
            }
        }
        return username
    }
}