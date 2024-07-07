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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.codegarden.CodeGardenApplication.Companion.appModule
import at.ac.fhcampuswien.codegarden.navigation.Screen
import at.ac.fhcampuswien.codegarden.viewModels.Badge
import at.ac.fhcampuswien.codegarden.viewModels.LeaderboardViewModel
import at.ac.fhcampuswien.codegarden.viewModels.viewModelFactory
import at.ac.fhcampuswien.codegarden.widgets.SimpleBottomAppBar
import at.ac.fhcampuswien.codegarden.widgets.SimpleTopAppBar

@Composable
fun LeaderBoardScreen(navController: NavController) {
    val viewModel: LeaderboardViewModel = viewModel(
        factory = viewModelFactory {
            LeaderboardViewModel(
                appModule.userService,
                appModule.sharedPrefManager
            )
        }
    )
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf("Leaderboard", "Achievements")

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Leaderboard",
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.ModuleScreen.route) }) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
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
                .padding(16.dp)
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
                0 -> LeaderboardContent(viewModel)
                1 -> AchievementsContent(viewModel)
            }
        }
    }
}

@Composable
fun LeaderboardContent(viewModel: LeaderboardViewModel) {
    val leaderboardItems by viewModel.leaderboardItems.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(leaderboardItems) { rankedUser ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${rankedUser.rank}. ${rankedUser.user.firstname}",
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${rankedUser.user.xpPoints.toInt()} XP",
                        modifier = Modifier.alignByBaseline()
                    )
                }
            }
        }
    }
}

@Composable
fun AchievementsContent(viewModel: LeaderboardViewModel) {
    val currentUser by viewModel.currentUser.collectAsState()
    val allBadges = viewModel.getAllBadges()
    val currentUserXp = currentUser?.xpPoints ?: 0f
    var badgeXP = 0f
    val xP = 384

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(allBadges) { badge ->
            badgeXP = if (xP >= badge.maxXP) badge.maxXP else xP.toFloat()
            val progress = (badgeXP / badge.maxXP) * 100

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
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
                    BadgeProgressBar(xpPoints = currentUserXp, badge = badge, badgeXP = badgeXP)
                }
            }
        }
    }
}

@Composable
fun BadgeProgressBar(xpPoints: Float, badge: Badge, badgeXP: Float) {
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