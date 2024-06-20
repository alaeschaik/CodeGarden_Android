package at.ac.fhcampuswien.codegarden.navigation


sealed class Screen(val route: String) {
    data object LoginScreen : Screen("login")
    data object PasswordResetScreen : Screen("reset")
    data object RegistrationScreen : Screen("registration")
}