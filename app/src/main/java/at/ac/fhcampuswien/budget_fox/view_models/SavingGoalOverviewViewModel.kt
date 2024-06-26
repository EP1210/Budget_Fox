package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.SavingGoal

class SavingGoalOverviewViewModel (
    val userId: String?
): ViewModel() {
    init {
        val userRepository = UserRepository()
        if(userId != null) {
            userRepository.getSavingGoalsFromUser(userId, onSuccess = { goals ->
                _savingGoals.addAll(goals)
            })
        }
    }

    private var _savingGoals = mutableStateListOf<SavingGoal>()
    val savingGoals: List<SavingGoal>
        get() = _savingGoals
}