package at.ac.fhcampuswien.codegarden.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.codegarden.CodeGardenApplication.Companion.appModule
import at.ac.fhcampuswien.codegarden.endpoints.posts.Comment
import at.ac.fhcampuswien.codegarden.endpoints.posts.Post
import at.ac.fhcampuswien.codegarden.navigation.Screen
import at.ac.fhcampuswien.codegarden.utils.convertTimestamp
import at.ac.fhcampuswien.codegarden.viewModels.CommunityViewModel
import at.ac.fhcampuswien.codegarden.viewModels.viewModelFactory
import at.ac.fhcampuswien.codegarden.widgets.SimpleBottomAppBar
import at.ac.fhcampuswien.codegarden.widgets.SimpleTopAppBar

@Composable
fun CommunityScreen(navController: NavController) {
    val viewModel = viewModel<CommunityViewModel>(
        factory = viewModelFactory {
            CommunityViewModel(
                appModule.postService,
                appModule.commentService,
                appModule.sharedPrefManager
            )
        }
    )
    val posts = viewModel.posts.collectAsState().value

    // Sort posts by number of upvotes in descending order
    val sortedPosts = posts.sortedByDescending { it.upvotes.intValue }

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Community",
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.ModuleScreen.route) }) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.CreatePostScreen.route) }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Create")
                    }
                },
            )
        },
        bottomBar = {
            SimpleBottomAppBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(sortedPosts) { post ->
                    PostCard(
                        viewModel = viewModel,
                        post = post,
                        onLikeClicked = {
                            post.upvotes.intValue += 1
                            viewModel.updatePost(post)
                        },
                        onDislikeClicked = {
                            post.downvotes.intValue += 1
                            viewModel.updatePost(post)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PostComments(
    postId: Int,
    comments: List<Comment>,
    showComments: Boolean,
    viewModel: CommunityViewModel,
    onCommentAdded: (Comment) -> Unit,
    onCommentDeleted: (Comment) -> Unit
) {
    AnimatedVisibility(
        visible = showComments,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column {
            comments.forEach { comment ->
                val username = viewModel.getUserNameForComment(comment.id)
                CommentItem(comment, username, viewModel) { onCommentDeleted(it) }
            }
            Spacer(modifier = Modifier.height(8.dp))
            val commentText = remember { mutableStateOf(TextFieldValue()) }

            Row(modifier = Modifier.padding(8.dp)) {
                BasicTextField(
                    value = commentText.value,
                    onValueChange = { commentText.value = it },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.LightGray, shape = CircleShape)
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    viewModel.createComment(postId, commentText.value.text) {
                        onCommentAdded(it.toComment())
                        commentText.value = TextFieldValue()
                        Toast.makeText(
                            appModule.applicationContext,
                            "Comment created!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                    Text(text = "Post")
                }
            }
        }
    }
}

@Composable
fun CommentItem(
    comment: Comment,
    username: String,
    viewModel: CommunityViewModel,
    onCommentDeleted: (Comment) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = username.firstOrNull().toString(),
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = username, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        viewModel.deleteComment(comment.id) {
                            onCommentDeleted(comment)
                            Toast.makeText(
                                appModule.applicationContext,
                                "Comment deleted!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.DeleteForever,
                        contentDescription = "Dislike"
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = comment.content, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Commented at: ${convertTimestamp(comment.createdAt)}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun PostCard(
    viewModel: CommunityViewModel,
    post: Post,
    onLikeClicked: () -> Unit,
    onDislikeClicked: () -> Unit,
) {
    var showComments by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("username") }
    username = viewModel.getUserNameForPost(post.id)
    var comments by remember { mutableStateOf(emptyList<Comment>()) }
    comments = viewModel.getCommentsForPost(post.id)
    var isEditing by remember { mutableStateOf(false) }
    var editableText by remember { mutableStateOf(post.content) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = username.firstOrNull().toString(),
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = username, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Posted at: ${convertTimestamp(post.createdAt)}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = {
                        isEditing = !isEditing
                        if (!isEditing) {
                            post.content = editableText
                            viewModel.updatePost(post)
                        }
                    },
                ) {
                    Text(if (isEditing) "Save" else "Edit")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (isEditing) {
                TextField(
                    value = editableText,
                    onValueChange = { editableText = it },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(text = editableText)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                IconButton(onClick = {
                    onLikeClicked()
                }) {
                    Icon(imageVector = Icons.Filled.ThumbUp, contentDescription = "Like")
                }
                Text(
                    text = "${post.upvotes.intValue}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.width(16.dp))

                IconButton(onClick = {
                    onDislikeClicked()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ThumbDown,
                        contentDescription = "Dislike"
                    )
                }
                Text(
                    text = "${post.downvotes.intValue}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.width(16.dp))

                IconButton(onClick = { showComments = !showComments }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Message,
                        contentDescription = "Comments"
                    )
                }
                Text(
                    text = "${comments.size}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
        PostComments(
            postId = post.id,
            comments = comments,
            showComments = showComments,
            viewModel = viewModel,
            onCommentAdded = { newComment ->
                comments += newComment
            },
            onCommentDeleted = { comment ->
                comments = comments.filter { it.id != comment.id }
            }
        )
    }
}