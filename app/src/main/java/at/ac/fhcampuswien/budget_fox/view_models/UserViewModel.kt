package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

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