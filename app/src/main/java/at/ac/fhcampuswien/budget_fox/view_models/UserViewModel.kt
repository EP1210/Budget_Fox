package at.ac.fhcampuswien.budget_fox.view_models

import android.util.Log
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.Category
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class UserViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val firebaseUser = Firebase.auth.currentUser

    private var _user = mutableStateOf<User?>(value = null).value
    val user: User?
        get() = _user

    private var _newUser = mutableStateOf(value = false).value
    val newUser: Boolean
        get() = _newUser

    private var _transactionDate = mutableStateOf(value = "").value
    val transactionDate: String
        get() = _transactionDate

    private var _transactionAmount = mutableDoubleStateOf(value = 0.0).doubleValue
    val transactionAmount: Double
        get() = _transactionAmount

    private var _transactionDescription = mutableStateOf(value = "").value
    val transactionDescription: String
        get() = _transactionDescription

    private var _categoryName = mutableStateOf(value = "").value
    val categoryName: String
        get() = _categoryName

    private var _categoryDescription = mutableStateOf(value = "").value
    val categoryDescription: String
        get() = _categoryDescription

    private var _categoriesFromUser = mutableStateListOf<Category>()
    val categoriesFromUser: List<Category>
        get() = _categoriesFromUser

    private var _transactionFrequency = mutableStateOf(value = "Monthly").value
    val transactionFrequency: String
        get() = _transactionFrequency

    private var _nextDueDate = mutableStateOf(value = Date()).value
    val nextDueDate: Date
        get() = _nextDueDate

    private val _transactions = mutableStateListOf<Transaction>()
    val transactions: List<Transaction>
        get() = _transactions

    fun setUser(user: User) {
        _user = user
    }

    fun setUserState(firstLogin: Boolean?) {
        _newUser = firstLogin ?: return
    }

    fun setTransactionDate(date: String) {
        _transactionDate = date
    }

    fun setTransactionAmount(amount: Double) {
        _transactionAmount = amount
    }

    fun setTransactionDescription(description: String) {
        _transactionDescription = description
    }

    fun setCategoryName(categoryName: String) {
        _categoryName = categoryName
    }

    fun setCategoryDescription(categoryDescription: String) {
        _categoryDescription = categoryDescription
    }

    fun insertCategory(category: Category) {
        firebaseUser?.let { userRepository.insertCategory(userId = it.uid, category = category) }
    }

    fun deleteCategory(categoryId: String) {
        if (firebaseUser != null) {
            userRepository.deleteCategory(userId = firebaseUser.uid, categoryId = categoryId)
        }
    }

    fun getCategoriesFromUser() {
        if (firebaseUser != null) {
            userRepository.getCategoriesFromUser(userId = firebaseUser.uid) { categories ->
                _categoriesFromUser.clear()
                _categoriesFromUser.addAll(categories)
            }
        }
    }

    fun setTransactionFrequency(frequency: String) {
        _transactionFrequency = frequency
        calculateNextDueDate()
    }

    fun loadTransactions() {
        firebaseUser?.uid?.let { userId ->
            userRepository.getTransactionsFromUser(userId, { fetchedTransactions ->
                _transactions.clear()
                _transactions.addAll(fetchedTransactions.sortedBy { it.date })
            }, { exception ->
                Log.w("UserViewModel", "Error loading transactions: ", exception)
            })
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        firebaseUser?.uid?.let { userId ->
            userRepository.deleteTransaction(userId, transaction.uuid) {
                _transactions.remove(transaction)
            }
        }
    }

    fun insertTransaction() {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date: Date? = format.parse(transactionDate)

        if (transactionAmount != 0.0 && transactionDescription.isNotBlank() && date != null && firebaseUser != null) {
            val transaction = Transaction(
                amount = transactionAmount,
                description = transactionDescription,
                date = date
            )

            userRepository.insertTransaction(
                transaction = transaction,
                userId = firebaseUser.uid,
                onSuccess = {
                    user?.addTransaction(transaction)
                    loadTransactions()
                })
            //TODO: Display error message
        }
    }

    fun insertRegularTransaction() {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date: Date? = format.parse(transactionDate)

        if (transactionAmount != 0.0 && transactionDescription.isNotBlank() && date != null && firebaseUser != null) {
            val transaction = Transaction(
                amount = transactionAmount,
                description = transactionDescription,
                date = date,
                isRegular = true,
                frequency = transactionFrequency,
                nextDueDate = nextDueDate
            )

            userRepository.insertTransaction(
                transaction = transaction,
                userId = firebaseUser.uid,
                onSuccess = {
                    user?.addTransaction(transaction)
                    loadTransactions()
                })
        }
    }

    private fun calculateNextDueDate() {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date: Date? = format.parse(_transactionDate)

        date?.let {
            val calendar = Calendar.getInstance()
            calendar.time = it
            when (_transactionFrequency) {
                "Daily" -> calendar.add(Calendar.DAY_OF_YEAR, 1)
                "Weekly" -> calendar.add(Calendar.WEEK_OF_YEAR, 1)
                "Monthly" -> calendar.add(Calendar.MONTH, 1)
                "Yearly" -> calendar.add(Calendar.YEAR, 1)
            }
            _nextDueDate = calendar.time
        }
    }
}