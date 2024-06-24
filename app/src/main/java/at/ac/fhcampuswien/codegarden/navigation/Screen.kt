package at.ac.fhcampuswien.codegarden.navigation


sealed class Screen(val route: String) {
    data object LoginScreen : Screen("login")
    data object PasswordResetScreen : Screen("reset")
    data object RegistrationScreen : Screen("registration")
    data object CommunityScreen : Screen("community")
    data object ModuleScreen : Screen("module")
    data object LeaderBoardScreen : Screen("leaderboard")
}