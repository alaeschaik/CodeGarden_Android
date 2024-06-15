package at.ac.fhcampuswien.codegarden

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import at.ac.fhcampuswien.codegarden.screens.LoginScreen
import at.ac.fhcampuswien.codegarden.ui.theme.CodeGardenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeGardenTheme {
                LoginScreen(
                    onLoginClick = { username, password ->
                        // Handle login logic here
                    },
                    onPasswordResetClick = {
                        // Handle password reset navigation here
                    },
                    onRegisterClick = {
                        // Handle registration navigation here
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodeGardenTheme {
        Greeting("Android")
    }
}