package at.ac.fhcampuswien.codegarden

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import at.ac.fhcampuswien.codegarden.navigation.Navigation
import at.ac.fhcampuswien.codegarden.ui.theme.CodeGardenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeGardenTheme {
                Navigation()
            }
        }
    }
}