package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.User

class UserViewModel : ViewModel() {

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

//cast to something

    private var _incomeDescription = mutableStateOf(value = "").value
    val incomeDescription: String
        get() = _incomeDescription

    private var _monthlyInterval = mutableStateOf(value = "").value
    val monthlyInterval: String
        get() = _monthlyInterval

    private var _incomeAmount = mutableStateOf(value = "").value
    val incomeAmount: String
        get() = _incomeAmount

    fun initializeUser(userId : String) {
        val repository = UserRepository()
        _user = repository.getUser(userId)
        _user?.addExpenses(repository.getExpensesFromUser(userId))
        _user?.addIncomes(repository.getIncomesFromUser(userId))
    }

    fun setIncomeDescription(description: String) {
        _incomeDescription = description
    }

    fun setMonthlyInterval(interval: String) {
        _monthlyInterval = interval
    }

    fun setIncomeAmount(amount: String) {
        _incomeAmount = amount
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
}