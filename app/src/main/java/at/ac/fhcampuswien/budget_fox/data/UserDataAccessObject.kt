package at.ac.fhcampuswien.budget_fox.data

import at.ac.fhcampuswien.budget_fox.models.Category
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.models.User

interface UserDataAccessObject {

    fun insertUser(user: User, userId: String)

    fun getUser(userId: String): User?

    fun deleteUser(userId: String)

    fun insertTransaction(userId: String, transaction: Transaction, onSuccess: () -> Unit)

    fun deleteTransaction(userId: String, transactionId: String)

    fun getTransactionsFromUser(userId: String, onSuccess: (List<Transaction>) -> Unit, onFailure: (Exception) -> Unit)

    fun insertCategory(userId: String, category: Category)

    fun getCategoriesFromUser(userId: String): List<Category>

    fun getAllDataFromUser(userId: String, onSuccess: (User?) -> Unit, onFailure: (Exception) -> Unit)

}