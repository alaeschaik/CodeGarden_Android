package at.ac.fhcampuswien.codegarden.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.ac.fhcampuswien.codegarden.navigation.Screen

@Composable
fun PasswordResetScreen(
    navController: NavController,
    onSendResetLinkClick: (String) -> Unit,
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Forgot Password?",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Send reset link button
        Button(
            onClick = { onSendResetLinkClick(email.text) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Send Reset Link")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Clickable text to go back to login
        Text(
            text = "Back to Login",
            modifier = Modifier
                .clickable(onClick = { navController.navigate(Screen.LoginScreen.route) })
                .padding(8.dp),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
