package at.ac.fhcampuswien.codegarden.viewModels

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.codegarden.endpoints.comments.CommentService
import at.ac.fhcampuswien.codegarden.endpoints.comments.CreateCommentRequest
import at.ac.fhcampuswien.codegarden.endpoints.comments.CreateCommentResponse
import at.ac.fhcampuswien.codegarden.endpoints.discussions.CreateDiscussionRequest
import at.ac.fhcampuswien.codegarden.endpoints.discussions.Discussion
import at.ac.fhcampuswien.codegarden.endpoints.discussions.DiscussionService
import at.ac.fhcampuswien.codegarden.endpoints.discussions.UpdateDiscussionRequest
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
    private val discussionService: DiscussionService,
    private val commentService: CommentService,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts = _posts.asStateFlow()

    private val _discussions = MutableStateFlow<List<Discussion>>(emptyList())
    val discussions = _discussions.asStateFlow()

    init {
        getAllPosts()
        getAllDiscussions()
    }

    // posts ---------------------------------------------------------------------------------------

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

    fun deletePost(postId: Int, onPostDeleted: () -> Unit) {
        viewModelScope.launch {
            postService.deletePost(postId).collect {
                _posts.value = _posts.value.filter { it.id != postId }
                Log.d("Post", "Deleted post with id $postId")
                onPostDeleted()
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
                    sortPosts()
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

    private fun sortPosts() {
        _posts.value =
            _posts.value.sortedByDescending { it.upvotes.intValue - it.downvotes.intValue }
    }

    private fun getAllPosts() {
        // Call the get all posts method from the PostService
        // If the posts were fetched successfully, call onPostsFetched
        viewModelScope.launch {
            postService.getAllPosts().collect { posts ->
                posts.let {
                    _posts.value = posts
                    sortPosts()
                }
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

    // comments ------------------------------------------------------------------------------------

    fun createComment(
        postId: Int,
        content: String,
        onCommentSuccess: (createCommentResponse: CreateCommentResponse) -> Unit
    ) {
        // Call the create comment method from the CommentService
        // If the comment was successful, call onCommentSuccess
        viewModelScope.launch {
            val userId = sharedPrefManager.fetchUserId() ?: -1
            val requestBody = CreateCommentRequest(postId, userId, content)
            commentService.createComment(requestBody).collect { response ->
                onCommentSuccess(response)
            }
        }
    }

    fun deleteComment(commentId: Int, onCommentDeleted: () -> Unit) {
        viewModelScope.launch {
            commentService.deleteComment(commentId).collect {
                Log.d("Comment", "Deleted comment with id $commentId")
                onCommentDeleted()
            }
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

    // discussions ---------------------------------------------------------------------------------
    fun createDiscussion(
        title: String,
        content: String,
        onDiscussionSuccess: (discussion: Discussion) -> Unit
    ) {
        // Call the create post method from the PostService
        // If the post was successful, call onDiscussionSuccess
        viewModelScope.launch {
            val userId = sharedPrefManager.fetchUserId() ?: -1
            val requestBody = CreateDiscussionRequest(title, content, userId)
            discussionService.createDiscussion(requestBody).collect { response ->
                if (response != null) {
                    onDiscussionSuccess(response.toDiscussion())
                }
            }
        }
    }

    private fun getAllDiscussions() {
        viewModelScope.launch {
            discussionService.getAllDiscussions().collect { discussions ->
                discussions.let {
                    _discussions.value = discussions
                }
            }
        }
    }

    @Composable
    fun getUserNameForDiscussion(discussionId: Int): String {
        var username by remember { mutableStateOf("") }

        LaunchedEffect(key1 = discussionId) {
            getUserForDiscussion(discussionId) { user ->
                username = user.username
            }
        }
        return username
    }

    private fun getUserForDiscussion(
        discussionId: Int,
        onUserFetched: (user: User) -> Unit
    ) {
        viewModelScope.launch {
            discussionService.getUserForDiscussion(discussionId).collect { user ->
                if (user != null) {
                    onUserFetched(user)
                }
            }
        }
    }

    fun deleteDiscussion(discussionId: Int, onDiscussionDeleted: () -> Unit) {
        viewModelScope.launch {
            discussionService.deleteDiscussion(discussionId).collect {
                _discussions.value = _discussions.value.filter { it.id != discussionId }
                Log.d("Discussion", "Deleted discussion with id $discussionId")
                onDiscussionDeleted()
            }
        }
    }

    fun updateDiscussion(discussion: Discussion) {
        viewModelScope.launch {
            discussionService.updateDiscussion(
                discussion.id,
                UpdateDiscussionRequest(
                    discussion.title,
                    discussion.content
                )
            ).collect { success ->
                if (success) {
                    _discussions.value = _discussions.value.map { if (it.id == discussion.id) discussion else it }.toList()
                }
            }
        }
    }
}