package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.SavingGoal

class SavingGoalAddViewModel(
    val userId: String?
) : ViewModel() {
    /*init {
        val userRepository = UserRepository()
        val savingGoal = SavingGoal(name = "Feier", amount = 12.0)
        savingGoal.addTransaction(Transaction(description = "ABCD", amount = 10.10, date = Date(2023, 1, 1)))
        if (userId != null) {
            userRepository.savingGoalToDatabase(userId, savingGoal, onSuccess = {})
        }
    }*/
    val userRepository = UserRepository()

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

    fun addSavingGoal(navigateBackOnSuccess: () -> Unit) {
        val savingGoal = SavingGoal(name = goalTitle, amount = goalAmount)

        if (userId != null) {
            userRepository.savingGoalToDatabase(userId, savingGoal, onSuccess = {
                navigateBackOnSuccess()
            })
        }
    }
}