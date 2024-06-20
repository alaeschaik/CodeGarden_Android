package at.ac.fhcampuswien.codegarden.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.ac.fhcampuswien.codegarden.navigation.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginClick: (String, String) -> Unit,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Log in", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(bottom = 16.dp))

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
            onClick = { onLoginClick(username, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Log in")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Clickable text for password reset
        Text(
            text = "Forgot your Password?",
            modifier = Modifier
                .clickable(onClick = {navController.navigate(Screen.PasswordResetScreen.route)})
                .padding(8.dp),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )

        // Clickable text for registration
        Text(
            text = "Don't have an account? Sign up here",
            modifier = Modifier
                .clickable(onClick = {navController.navigate(Screen.RegistrationScreen.route)})
                .padding(8.dp),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
