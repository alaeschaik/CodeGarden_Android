package at.ac.fhcampuswien.codegarden.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.RssFeed
import androidx.compose.material.icons.filled.School
import androidx.compose.ui.graphics.vector.ImageVector
import at.ac.fhcampuswien.codegarden.navigation.Screen

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object ModuleScreen: BottomBarScreen(
        route = Screen.ModuleScreen.route,
        title = Screen.ModuleScreen.title.toString(),
        icon = Icons.Filled.School
    )

    data object CommunityScreen: BottomBarScreen(
        route = Screen.CommunityScreen.route,
        title = Screen.CommunityScreen.title.toString(),
        icon = Icons.Filled.RssFeed
    )

    data object LeaderBoard: BottomBarScreen(
        route = Screen.LeaderBoardScreen.route,
        title = Screen.LeaderBoardScreen.title.toString(),
        icon = Icons.Filled.EmojiEvents
    )
}