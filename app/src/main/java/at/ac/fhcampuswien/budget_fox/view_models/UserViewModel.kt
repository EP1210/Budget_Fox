package at.ac.fhcampuswien.budget_fox.view_models

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
import java.util.Date

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val firebaseUser = Firebase.auth.currentUser

    val numbersVisible = mutableStateOf(value = true)

    private var _user = mutableStateOf<User?>(value = null).value
    val user: User?
        get() = _user

    private var _firstLogin = mutableStateOf(value = false).value
    val firstLogin: Boolean
        get() = _firstLogin

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

    private var _categoryIdsAtTransaction = mutableStateListOf<String>()
    val categoryIdsAtTransaction: List<String>
        get() = _categoryIdsAtTransaction

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

    fun getUserId():String {
        return firebaseUser?.uid ?: ""
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

    fun insertCategoryAtUser(category: Category) {
        if (firebaseUser != null) {
            userRepository.insertCategoryAtUser(userId = firebaseUser.uid, category = category)
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

    fun deleteCategoryAtUser(categoryId: String) {
        if (firebaseUser != null) {
            userRepository.deleteCategoryAtUser(userId = firebaseUser.uid, categoryId = categoryId)
        }
    }

    fun insertCategoryAtTransaction(categoryId: String, transactionId: String) {
        if (firebaseUser != null) {
            userRepository.insertCategoryAtTransaction(
                userId = firebaseUser.uid,
                categoryId = categoryId,
                transactionId = transactionId
            )
        }
    }

    fun getIdsFromCategoriesAtTransaction(transactionId: String) {
        if (firebaseUser != null) {
            userRepository.getIdsFromCategoriesAtTransaction(
                userId = firebaseUser.uid,
                transactionId = transactionId
            ) { categoryIds ->
                _categoryIdsAtTransaction.clear()
                _categoryIdsAtTransaction.addAll(categoryIds)
            }
        }
    }

    fun deleteCategoryAtTransaction(transactionId: String, categoryId: String) {
        if (firebaseUser != null) {
            userRepository.deleteCategoryAtTransaction(
                userId = firebaseUser.uid,
                transactionId = transactionId,
                categoryId = categoryId
            )
        }
    }

    fun deleteCategoryAtAllTransactions(categoryId: String) {
        if (firebaseUser != null) {
            userRepository.deleteCategoryAtAllTransactions(
                userId = firebaseUser.uid,
                categoryId = categoryId
            )
        }
    }


    fun getHousehold() : String {
        if(user != null && user?.householdId != "") {
            return user!!.householdId
        }
        return ""
    }

    fun joinHousehold(householdId: String, onSuccess: ()->Unit = {}, notExists: ()->Unit = {}) {
        //TODO: Check race conditions! - Household is inserted in other VM
        userRepository.joinHouseholdIfExist(userId = firebaseUser!!.uid, householdId = householdId, onSuccess = {
            user?.joinHousehold(householdId)
            onSuccess()
        }, notExits = {
            notExists()
        })
    }

    fun leaveHousehold(userId: String): Boolean {
        if(userId == getUserId()) {
            _user?.householdId = ""
            _user?.let {
                userRepository.insertUser(it, userId)
                return true
            }
        }

        return false
    }
}