package at.ac.fhcampuswien.budget_fox.view_models

import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.SavingGoal
import kotlinx.coroutines.flow.MutableStateFlow

class SavingGoalOverviewViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _goals = MutableStateFlow<List<SavingGoal>>(mutableListOf())

    fun loadSavingGoals(userId: String) : MutableStateFlow<List<SavingGoal>> {
        userRepository.getSavingGoalsFromUser(userId, onSuccess = { goals ->
            _goals.value = goals
        })
        return _goals
    }
}