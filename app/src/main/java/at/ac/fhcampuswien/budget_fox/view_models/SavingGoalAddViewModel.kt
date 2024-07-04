package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.SavingGoal

class SavingGoalAddViewModel: ViewModel() {
    private val userRepository = UserRepository()

    //region input fields
    private var _goalTitle = mutableStateOf(value = "").value
    val goalTitle: String
        get() = _goalTitle

    private var _goalAmount = mutableStateOf(value = 0.0).value
    val goalAmount: Double
        get() = _goalAmount

    fun setGoalTitle(goalTitle: String) {
        _goalTitle = goalTitle
    }

    fun setGoalAmount(goalAmount: Double) {
        _goalAmount = goalAmount
    }
    //endregion

    fun addSavingGoal(userId: String, navigateBackOnSuccess: () -> Unit) {
        val savingGoal = SavingGoal(name = goalTitle, amount = goalAmount)

        userRepository.savingGoalToDatabase(userId, savingGoal, onSuccess = {
            navigateBackOnSuccess()
        })
    }
}