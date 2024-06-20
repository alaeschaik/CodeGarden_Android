package at.ac.fhcampuswien.codegarden.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.ac.fhcampuswien.codegarden.screens.CommunityScreen
import at.ac.fhcampuswien.codegarden.screens.LoginScreen
import at.ac.fhcampuswien.codegarden.screens.PasswordResetScreen
import at.ac.fhcampuswien.codegarden.screens.RegistrationScreen

@Composable
fun Navigation() {
    val navController = rememberNavController() // create a NavController instance

    NavHost(navController = navController, // pass the NavController to NavHost
        startDestination = Screen.LoginScreen.route) {  // pass a start destination
        composable(route = Screen.LoginScreen.route){
            LoginScreen(
                navController = navController,
                onLoginClick = { username, password ->
                    // Handle login logic here
                }
            )
        }

        composable(route = Screen.RegistrationScreen.route) {
            RegistrationScreen(
                navController = navController,
                onRegister = {
                    // Handle registration logic here
                }
            )
        }

        composable(route = Screen.PasswordResetScreen.route){
            PasswordResetScreen(
                navController = navController,
                onSendResetLinkClick = { email ->
                    // Handle password reset logic here
                }
            )
        }

        composable(route = Screen.CommunityScreen.route){
            CommunityScreen(
                navController = navController
            )
        }
    }
}