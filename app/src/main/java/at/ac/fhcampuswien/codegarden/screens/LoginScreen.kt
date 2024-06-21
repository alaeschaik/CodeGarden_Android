package at.ac.fhcampuswien.codegarden.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.codegarden.CodeGardenApplication.Companion.appModule
import at.ac.fhcampuswien.codegarden.navigation.Screen
import at.ac.fhcampuswien.codegarden.ui.viewmodels.LoginViewModel
import at.ac.fhcampuswien.codegarden.ui.viewmodels.viewModelFactory

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel = viewModel<LoginViewModel>(
        factory = viewModelFactory {
            LoginViewModel(appModule.userService, appModule.sharedPrefManager)
        }
    )

    if (viewModel.isUserLoggedIn()) {
        navController.navigate(Screen.CommunityScreen.route)
    }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Log in",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Username field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Login button
        Button(
            onClick = {
                viewModel.login(
                    username,
                    password
                ) {
                    navController.navigate(Screen.CommunityScreen.route)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Log in")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Clickable text for password reset
        Text(
            text = "Forgot your Password?",
            modifier = Modifier
                .clickable(onClick = { navController.navigate(Screen.PasswordResetScreen.route) })
                .padding(8.dp),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )

        // Clickable text for registration
        Text(
            text = "Don't have an account? Sign up here",
            modifier = Modifier
                .clickable(onClick = { navController.navigate(Screen.RegistrationScreen.route) })
                .padding(8.dp),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
