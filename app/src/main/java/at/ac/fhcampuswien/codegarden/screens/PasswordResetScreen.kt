package at.ac.fhcampuswien.codegarden.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.codegarden.CodeGardenApplication.Companion.appModule
import at.ac.fhcampuswien.codegarden.navigation.Screen
import at.ac.fhcampuswien.codegarden.ui.viewmodels.ResetPasswordViewModel
import at.ac.fhcampuswien.codegarden.ui.viewmodels.viewModelFactory

@Composable
fun PasswordResetScreen(
    navController: NavController
) {
    var viewmodel = viewModel<ResetPasswordViewModel>(
        factory = viewModelFactory {
            ResetPasswordViewModel(appModule.userService)
        }
    )
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var oldPassword by remember { mutableStateOf(TextFieldValue("")) }
    var newPassword by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Let's reset your password!",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Username field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username or email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )

        // OldPassword field
        OutlinedTextField(
            value = oldPassword,
            onValueChange = { oldPassword = it },
            label = { Text("Old password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        // NewPassword field
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Send reset link button
        Button(
            onClick = {
                viewmodel.resetPassword(
                    username.text,
                    oldPassword.text,
                    newPassword.text,
                    onResetSuccess = {
                        navController.navigate(Screen.LoginScreen.route)
                    })
            },
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
