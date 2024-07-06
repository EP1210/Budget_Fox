package at.ac.fhcampuswien.budget_fox.view_models

import android.util.Log
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.Repository
import at.ac.fhcampuswien.budget_fox.models.Category
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class UserViewModel : ViewModel() {
    private val userRepository = Repository()
    private val firebaseUser = Firebase.auth.currentUser

    val numbersVisible = mutableStateOf(value = true)

    var transactionMessage = mutableStateOf<String?>(null)
        private set

    private var _user = mutableStateOf<User?>(value = null).value
    val user: User?
        get() = _user

    private var _firstLogin = mutableStateOf(value = false).value
    val firstLogin: Boolean
        get() = _firstLogin

    private var _transactionDate = mutableStateOf(value = getCurrentDateString()).value
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
        _firstLogin = firstLogin ?: return
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

    fun getUserId(): String {
        return firebaseUser?.uid ?: ""
    }

    fun setCategoryName(categoryName: String) {
        _categoryName = categoryName
    }

    fun setCategoryDescription(categoryDescription: String) {
        _categoryDescription = categoryDescription
    }

    fun insertCategory(category: Category) {
        if (firebaseUser != null) {
            userRepository.insertCategory(userId = firebaseUser.uid, category = category)
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

    fun updateCategoryTransactionMemberships(category: Category) {
        if (firebaseUser != null) {
            userRepository.updateCategoryTransactionMemberships(
                userId = firebaseUser.uid,
                category = category
            )
        }
    }

    fun deleteCategory(categoryId: String) {
        if (firebaseUser != null) {
            userRepository.deleteCategory(userId = firebaseUser.uid, categoryId = categoryId)
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
                    transactionMessage.value = "Transaction added successfully!"
                },
                onFailure = {
                    transactionMessage.value = "Error adding transaction: ${it.message}."
                }
            )
        } else {
            transactionMessage.value = "Invalid transaction data."
        }
    }

    fun insertRegularTransaction() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val inputDate: Date? = dateFormat.parse(transactionDate)

        if (transactionAmount != 0.0 && transactionDescription.isNotBlank() && inputDate != null && firebaseUser != null) {
            val calendar = Calendar.getInstance()
            val currentTime = calendar.time

            val calendarDate = Calendar.getInstance()
            calendarDate.time = inputDate
            val calendarTime = Calendar.getInstance()
            calendarTime.time = currentTime

            calendarDate.set(Calendar.HOUR_OF_DAY, calendarTime.get(Calendar.HOUR_OF_DAY))
            calendarDate.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE))
            calendarDate.set(Calendar.SECOND, calendarTime.get(Calendar.SECOND))

            val combinedDateTime = calendarDate.time

            val transaction = Transaction(
                amount = transactionAmount,
                description = transactionDescription,
                date = combinedDateTime,
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
                    transactionMessage.value = "Regular transaction added successfully!"
                },
                onFailure = {
                    transactionMessage.value = "Error adding regular transaction: ${it.message}"
                }
            )
        } else {
            transactionMessage.value = "Invalid regular transaction data."
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

    fun getHousehold() : String {
        if(user != null && user?.householdId != "") {
            return user!!.householdId
        }
        return ""
    }

    fun joinHousehold(householdId: String, onSuccess: () -> Unit = {}, notExists: () -> Unit = {}) {
        //TODO: Check race conditions! - Household is inserted in other VM
        userRepository.joinHouseholdIfExist(
            userId = firebaseUser!!.uid,
            householdId = householdId,
            onSuccess = {
                user?.joinHousehold(householdId)
                onSuccess()
            },
            notExits = {
                notExists()
            })
    }

    fun leaveHousehold(userId: String): Boolean {
        if (userId == getUserId()) {
            _user?.householdId = ""
            _user?.let {
                userRepository.insertUser(it, userId)
                return true
            }
        }

        return false
    }

    private fun getCurrentDateString(): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(Date())
    }
}