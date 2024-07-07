package at.ac.fhcampuswien.codegarden.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.codegarden.endpoints.users.User
import at.ac.fhcampuswien.codegarden.endpoints.users.UserService
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Badge(val name: String, val minXP: Float, val maxXP: Float)
data class RankedUser(val rank: Int, val user: User)

class LeaderboardViewModel(
    private val userService: UserService,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private val _leaderboardItems = MutableStateFlow<List<RankedUser>>(emptyList())
    val leaderboardItems: StateFlow<List<RankedUser>> get() = _leaderboardItems

    private val _userProfile = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> get() = _userProfile

    private val badges = listOf(
        Badge("Noob", 0f, 100f),
        Badge("Beginner", 100f, 500f),
        Badge("Intermediate", 500f, 1000f),
        Badge("Advanced", 1000f, 2500f),
        Badge("Expert", 2500f, 10000f)
    )

    init {
        viewModelScope.launch {
            val userId = sharedPrefManager.fetchUserId()
            if (userId != null) {
                loadUserProfile(userId)
            }
            fetchLeaderboardItems()
        }
    }

    private fun fetchLeaderboardItems() {
        viewModelScope.launch {
            val users = userService.getAllUsers().sortedByDescending { it.xpPoints }
            val rankedUsers = assignRanks(users)
            _leaderboardItems.value = rankedUsers
        }
    }

    private fun loadUserProfile(userId: Int) {
        viewModelScope.launch {
            val user = userService.getUserProfile(userId)
            user?.let {
                _userProfile.value = it
            }
        }
    }

    private fun assignRanks(users: List<User>): List<RankedUser> {
        if (users.isEmpty()) return emptyList()
        val rankedUsers = mutableListOf<RankedUser>()
        var currentRank = 1
        for (i in users.indices) {
            if (i > 0 && users[i].xpPoints < users[i - 1].xpPoints) {
                currentRank = i + 1
            }
            rankedUsers.add(RankedUser(currentRank, users[i]))
        }
        return rankedUsers
    }

    fun getAllBadges(): List<Badge> {
        return badges
    }
}