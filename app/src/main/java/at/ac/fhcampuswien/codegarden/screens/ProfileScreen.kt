package at.ac.fhcampuswien.codegarden.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.codegarden.CodeGardenApplication.Companion.appModule
import at.ac.fhcampuswien.codegarden.navigation.Screen
import at.ac.fhcampuswien.codegarden.viewModels.Badge
import at.ac.fhcampuswien.codegarden.viewModels.ProfileViewModel
import at.ac.fhcampuswien.codegarden.viewModels.viewModelFactory
import at.ac.fhcampuswien.codegarden.widgets.SimpleBottomAppBar
import at.ac.fhcampuswien.codegarden.widgets.SimpleTopAppBar

@Composable
fun ProfileScreen(navController: NavController) {
    val viewModel: ProfileViewModel = viewModel(
        factory = viewModelFactory {
            ProfileViewModel(appModule.userService, appModule.sharedPrefManager)
        }
    )
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Profile", "Achievements")

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Profile",
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.logout {
                            navController.navigate(Screen.LoginScreen.route) {
                                popUpTo(Screen.ProfileScreen.route) { inclusive = true }
                            }
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
                    }
                }
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }

            when (selectedTab) {
                0 -> ProfileContent(viewModel)
                1 -> AchievementContent(viewModel)
            }

        }
    }
}

@Composable
fun ProfileContent(viewModel: ProfileViewModel) {
    val username by viewModel.username.collectAsState()
    val email by viewModel.email.collectAsState()
    val firstname by viewModel.firstname.collectAsState()
    val lastname by viewModel.lastname.collectAsState()

    Spacer(modifier = Modifier.height(8.dp))
    // Username field
    OutlinedTextField(
        value = username,
        onValueChange = {
            viewModel.updateUsername(it)
        },
        label = { Text("Username") },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Email field
    OutlinedTextField(
        value = email,
        onValueChange = { viewModel.updateEmail(it) },
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Firstname field
    OutlinedTextField(
        value = firstname,
        onValueChange = { viewModel.updateFirstname(it) },
        label = { Text("Firstname") },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Lastname field
    OutlinedTextField(
        value = lastname,
        onValueChange = { viewModel.updateLastname(it) },
        label = { Text("Lastname") },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Save button
    Button(
        onClick = {
            viewModel.updateProfile(username, email, firstname, lastname)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Save")
    }
}

@Composable
fun AchievementContent(viewModel: ProfileViewModel) {
    val currentUser by viewModel.currentUser.collectAsState()
    val allBadges = viewModel.getAllBadges()
    val currentUserXp = currentUser?.xpPoints ?: 0f
    var badgeXP: Float
    //val xP = 384

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(allBadges) { badge ->
            badgeXP = if (currentUserXp >= badge.maxXP) badge.maxXP else currentUserXp
            val progress = (badgeXP / badge.maxXP) * 100

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = badge.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "${progress.toInt()}%",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    BadgeProgressBar(badge = badge, badgeXP = badgeXP)
                }
            }
        }
    }
}

@Composable
fun BadgeProgressBar(badge: Badge, badgeXP: Float) {
    val progress = (badgeXP / badge.maxXP).coerceIn(0f, 1f)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "XP: ${badgeXP.toInt()}/${badge.maxXP.toInt()}",
            style = MaterialTheme.typography.bodyMedium
        )
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .padding(top = 4.dp),
            color = MaterialTheme.colorScheme.primary,
        )
    }
}