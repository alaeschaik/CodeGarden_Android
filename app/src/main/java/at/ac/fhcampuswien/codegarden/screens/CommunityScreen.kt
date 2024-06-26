package at.ac.fhcampuswien.codegarden.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.text.font.FontWeight
import at.ac.fhcampuswien.codegarden.navigation.Screen
import at.ac.fhcampuswien.codegarden.widgets.SimpleBottomAppBar
import at.ac.fhcampuswien.codegarden.widgets.SimpleTopAppBar

@Composable
fun CommunityScreen(navController: NavController) {
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
                    IconButton(onClick = { /* Handle create action */ }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Create")
                    }
                })
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
            val postText = remember { mutableStateOf(TextFieldValue()) }

            Row {
                BasicTextField(
                    value = postText.value,
                    onValueChange = { postText.value = it },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.LightGray, shape = CircleShape)
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { /* Submit post action */ }) {
                    Text(text = "Post")
                }
            }

            PostCard(
                navController = navController,
                name = "Elise",
                content = "Mobile App Development is cool!",
                initLikes = 115,
                initComments = 0,
                initDislikes = 69,
                onLikeClicked = { /* Handle like action */ },
                onDislikeClicked = { /* Handle dislike action */ },
                onCardClicked = { /* Handle card click action */ }
            )

            PostCard(
                navController = navController,
                name = "Elise",
                content = "Mobile App Development is cool!",
                initLikes = 115,
                initComments = 0,
                initDislikes = 69,
                onLikeClicked = { /* Handle like action */ },
                onDislikeClicked = { /* Handle dislike action */ },
                onCardClicked = { /* Handle card click action */ }
            )
        }
    }
}

@Composable
fun PostCard(
    navController: NavController,
    name: String,
    content: String,
    initLikes: Int,
    initComments: Int,
    initDislikes: Int,
    onLikeClicked: () -> Unit,
    onDislikeClicked: () -> Unit,
    onCardClicked: () -> Unit
) {
    val likes by remember { mutableIntStateOf(initLikes) }
    val dislikes by remember { mutableIntStateOf(initDislikes) }
    val comments by remember { mutableIntStateOf(initComments) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCardClicked),
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
                        text = name.first().toString(),
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = name, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = content)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                IconButton(onClick = {
                    onLikeClicked()
                }) {
                    Icon(imageVector = Icons.Filled.ThumbUp, contentDescription = "Like")
                }
                Text(text = "$likes", modifier = Modifier.align(Alignment.CenterVertically))

                Spacer(modifier = Modifier.width(16.dp))

                IconButton(onClick = {
                    onDislikeClicked()
                }) {
                    Icon(imageVector = Icons.Filled.ThumbDown, contentDescription = "Dislike")
                }
                Text(text = "$dislikes", modifier = Modifier.align(Alignment.CenterVertically))

                Spacer(modifier = Modifier.width(16.dp))

                IconButton(onClick = onCardClicked) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Message, contentDescription = "Comments")
                }
                Text(text = "$comments", modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
    }
}