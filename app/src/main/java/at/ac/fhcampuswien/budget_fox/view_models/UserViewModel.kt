package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.Category
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.text.SimpleDateFormat
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

    private var _transactionAmount = mutableStateOf(value = 0.0).value
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

    private var _categoriesFromUser = listOf<Category>()
    val categoriesFromUser: List<Category>
        get() = _categoriesFromUser


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
                })
            //TODO: Display error message
        }
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

    private fun setCategoriesFromUser() {
        if (firebaseUser != null) {
            _categoriesFromUser = userRepository.getCategoriesFromUser(userId = firebaseUser.uid)
        }
    }

    init {
        setCategoriesFromUser()
    }
}