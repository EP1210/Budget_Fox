package at.ac.fhcampuswien.budget_fox.view_models

import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.SavingGoal
import at.ac.fhcampuswien.budget_fox.models.Transaction
import java.util.Date

class SavingGoalAddViewModel(
    userId: String?
) : ViewModel() {
    /*init {
        val userRepository = UserRepository()
        val savingGoal = SavingGoal(name = "Feier", amount = 12.0)
        savingGoal.addTransaction(Transaction(description = "ABCD", amount = 10.10, date = Date(2023, 1, 1)))
        if (userId != null) {
            userRepository.savingGoalToDatabase(userId, savingGoal, onSuccess = {})
        }
    }*/
}