package at.ac.fhcampuswien.codegarden.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.codegarden.endpoints.users.User
import at.ac.fhcampuswien.codegarden.endpoints.users.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    private val userService: UserService,
) : ViewModel() {

    private val _leaderboardItems = MutableStateFlow<List<RankedUser>>(emptyList())
    val leaderboardItems: StateFlow<List<RankedUser>> get() = _leaderboardItems

    init {
        fetchLeaderboardItems()
    }

    private fun fetchLeaderboardItems() {
        viewModelScope.launch {
            val users = userService.getAllUsers().sortedByDescending { it.xpPoints }
            val rankedUsers = assignRanks(users)
            _leaderboardItems.value = rankedUsers
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
}

data class RankedUser(val rank: Int, val user: User)