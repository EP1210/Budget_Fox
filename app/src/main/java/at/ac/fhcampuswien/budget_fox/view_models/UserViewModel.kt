package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.time.Period
import java.util.UUID

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

    private var _transactionAmount = mutableStateOf(value = 0.0).value
    val transactionAmount: Double
        get() = _transactionAmount

    private var _transactionDescription = mutableStateOf(value = "").value
    val transactionDescription: String
        get() = _transactionDescription

    private var _monthlyInterval = mutableStateOf(value = "").value
    val monthlyInterval: String //TODO: Transaction period
        get() = _monthlyInterval

    fun setUser(user: User) {
        _user = user
    }

    fun setMonthlyInterval(interval: String) {
        _monthlyInterval = interval
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

    fun insertTransaction() {
        if (transactionAmount != 0.0 && transactionDescription.isNotBlank() && firebaseUser != null) {
            val transaction = Transaction(
                uuid = UUID.randomUUID().toString(),
                amount = transactionAmount,
                description = transactionDescription,
                /*period = when {
                    monthlyInterval.isNotBlank() -> Period.ofMonths(monthlyInterval.toInt())
                    else -> null
                }*/ //TODO: Period
            )
            userRepository.insertTransaction(transaction = transaction, userId = firebaseUser.uid, onSuccess = {
                user?.addTransaction(transaction)
            })
        }
    }
}