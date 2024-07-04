package at.ac.fhcampuswien.budget_fox.data

import at.ac.fhcampuswien.budget_fox.models.Category
import at.ac.fhcampuswien.budget_fox.models.SavingGoal
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.models.User

interface UserDataAccessObject {

    fun insertUser(user: User, userId: String, onSuccess: () -> Unit = {})

    fun getUser(userId: String): User?

    fun deleteUser(userId: String)

    fun insertTransaction(userId: String, transaction: Transaction, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)

    fun getTransactionsFromUser(userId: String, onSuccess: (List<Transaction>) -> Unit, onFailure: (Exception) -> Unit)

    fun deleteTransaction(userId: String, transactionId: String, onComplete: () -> Unit)

    fun insertCategory(userId: String, category: Category)

    fun getCategoriesFromUser(userId: String, onSuccess: (List<Category>) -> Unit)

    fun updateCategoryTransactionMemberships(userId: String, category: Category)

    fun deleteCategory(userId: String, categoryId: String)

    fun getAllDataFromUser(userId: String, onSuccess: (User?) -> Unit, onFailure: (Exception) -> Unit)

    fun getSavingGoalsFromUser(userId: String, onSuccess: (List<SavingGoal>) -> Unit)

    fun getSavingGoalsTransactions(userId: String, goals: List<SavingGoal>, onSuccess: (List<SavingGoal>) -> Unit)

    fun savingGoalToDatabase(userId: String, savingGoal: SavingGoal, onSuccess: () -> Unit)
}