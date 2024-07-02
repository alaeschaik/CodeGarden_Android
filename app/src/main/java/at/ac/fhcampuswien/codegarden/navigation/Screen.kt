package at.ac.fhcampuswien.codegarden.navigation


sealed class Screen(val route: String, val title: String? = null) {
    data object LoginScreen : Screen("login")
    data object PasswordResetScreen : Screen("reset")
    data object RegistrationScreen : Screen("registration")
    data object CommunityScreen : Screen("community", "Community")
    data object ModuleScreen : Screen("module", "Home")
    data object LeaderBoardScreen : Screen("leaderboard", "Leaderboard")
    data object ProfileScreen : Screen("profile")
    data object CreatePostScreen : Screen("createPost", "Create Post")
    data object CreateModuleScreen : Screen("createModule", "Create Module")
    data object ModuleDetailScreen : Screen("moduleDetail", "Module Detail")
}
