package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.User

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private var _user = mutableStateOf<User?>(value = null).value
    val user: User?
        get() = _user

    private var _newUser = mutableStateOf(value = false).value
    val newUser: Boolean
        get() = _newUser
    private var _expenseDate = mutableStateOf(value = "").value
    val expenseDate: String
        get() = _expenseDate
    private var _expenseAmount = mutableStateOf(value = "").value
    val expenseAmount: String
        get() = _expenseAmount
    private var _expenseDescription = mutableStateOf(value = "").value
    val expenseDescription: String
        get() = _expenseDescription

    fun setUser(user: User) {
        _user = user
    }

    fun setUserState(firstLogin: Boolean?) {
        _newUser = firstLogin ?: return
    }

    fun setExpenseDate(date: String) {
        _expenseDate = date
    }

    fun setExpenseAmount(amount: String) {
        _expenseAmount = amount
    }

    fun setExpenseDescription(description: String) {
        _expenseDescription = description
    }

    fun getUserFromDatabase(uid: String) {
        _user = userRepository.getUser(userId = uid)
        _user?.setUid(uid)

        //this.user?.let { userRepository.getIncomes(it) }
        //this.user?.let { userRepository.getExpenses(it) }
    }
}