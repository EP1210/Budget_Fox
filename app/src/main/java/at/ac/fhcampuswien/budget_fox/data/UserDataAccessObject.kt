package at.ac.fhcampuswien.budget_fox.data

import at.ac.fhcampuswien.budget_fox.models.Category
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.models.User

interface UserDataAccessObject {

    fun insertUser(user: User, userId: String)

    fun getUser(userId: String): User?

    fun deleteUser(userId: String)

    fun insertTransaction(userId: String, transaction: Transaction, onSuccess: () -> Unit)

    fun getTransactionsFromUser(userId: String, onSuccess: (List<Transaction>) -> Unit, onFailure: (Exception) -> Unit)

    fun deleteTransaction(userId: String, transactionId: String)

    fun insertCategoryAtUser(userId: String, category: Category)

    fun getCategoriesFromUser(userId: String, onSuccess: (List<Category>) -> Unit)

    fun deleteCategoryAtUser(userId: String, categoryId: String)

    fun insertCategoryAtTransaction(userId: String, categoryId: String, transactionId: String)

    fun getCategoriesFromTransaction(userId: String, transactionId: String, onSuccess: (List<Category>) -> Unit)

    fun getIdsFromCategoriesAtTransaction(userId: String, transactionId: String, onSuccess: (List<String>) -> Unit)

    fun deleteCategoryAtTransaction(userId: String, transactionId: String, categoryId: String)

    fun deleteCategoryAtAllTransactions(userId: String, categoryId: String)

    fun getAllDataFromUser(userId: String, onSuccess: (User?) -> Unit, onFailure: (Exception) -> Unit)
}