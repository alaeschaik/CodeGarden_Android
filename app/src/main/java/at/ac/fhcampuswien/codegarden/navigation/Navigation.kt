package at.ac.fhcampuswien.codegarden.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.ac.fhcampuswien.codegarden.screens.ChallengeScreen
import at.ac.fhcampuswien.codegarden.screens.ChoiceScreen
import at.ac.fhcampuswien.codegarden.screens.CommunityScreen
import at.ac.fhcampuswien.codegarden.screens.CreateModuleScreen
import at.ac.fhcampuswien.codegarden.screens.CreatePostScreen
import at.ac.fhcampuswien.codegarden.screens.LeaderBoardScreen
import at.ac.fhcampuswien.codegarden.screens.LoginScreen
import at.ac.fhcampuswien.codegarden.screens.ModuleDetailScreen
import at.ac.fhcampuswien.codegarden.screens.ModuleScreen
import at.ac.fhcampuswien.codegarden.screens.PasswordResetScreen
import at.ac.fhcampuswien.codegarden.screens.ProfileScreen
import at.ac.fhcampuswien.codegarden.screens.QuestionScreen
import at.ac.fhcampuswien.codegarden.screens.RegistrationScreen

@Composable
fun Navigation() {
    val navController = rememberNavController() // create a NavController instance

    NavHost(
        navController = navController, // pass the NavController to NavHost
        startDestination = Screen.LoginScreen.route
    ) {  // pass a start destination
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(route = Screen.RegistrationScreen.route) {
            RegistrationScreen(navController = navController)
        }

        composable(route = Screen.PasswordResetScreen.route) {
            PasswordResetScreen(navController = navController)
        }

        composable(route = Screen.CommunityScreen.route) {
            CommunityScreen(navController = navController)
        }

        composable(route = Screen.LeaderBoardScreen.route) {
            LeaderBoardScreen(navController = navController)
        }

        composable(route = Screen.ModuleScreen.route) {
            ModuleScreen(navController = navController)
        }

        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }

        composable(route = Screen.CreatePostScreen.route) {
            CreatePostScreen(navController = navController)
        }

        composable(route = Screen.CreateModuleScreen.route) {
            CreateModuleScreen(navController = navController)
        }

        composable("${Screen.ModuleDetailScreen.route}/{moduleId}") { backStackEntry ->
            val moduleId = backStackEntry.arguments?.getString("moduleId")?.toInt() ?: return@composable
            ModuleDetailScreen(moduleId = moduleId, navController = navController)
        }

        composable("${Screen.ChallengeScreen.route}/{sectionId}") { backStackEntry ->
            val sectionId = backStackEntry.arguments?.getString("sectionId")?.toInt() ?: return@composable
            ChallengeScreen(sectionId = sectionId, navController = navController)
        }

        composable("${Screen.QuestionScreen.route}/{challengeId}") { backStackEntry ->
            val challengeId = backStackEntry.arguments?.getString("challengeId")?.toInt() ?: return@composable
            QuestionScreen(challengeId = challengeId, navController = navController)
        }

        composable("${Screen.ChoiceScreen.route}/{questionId}") { backStackEntry ->
            val questionId =
                backStackEntry.arguments?.getString("questionId")?.toInt() ?: return@composable
            ChoiceScreen(questionId = questionId, navController = navController)
        }
    }
}
