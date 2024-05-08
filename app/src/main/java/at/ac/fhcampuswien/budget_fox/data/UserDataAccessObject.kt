package at.ac.fhcampuswien.budget_fox.data

import at.ac.fhcampuswien.budget_fox.models.Expense
import at.ac.fhcampuswien.budget_fox.models.Income
import at.ac.fhcampuswien.budget_fox.models.User

interface UserDataAccessObject {

    fun insertUser(user: User, userId: String)

    fun getUser(userId: String): User?

    fun deleteUser(userId: String)

    fun insertIncome(income: Income, userId: String)

    fun deleteIncome(income: Income, userId: String)

    fun insertExpense(userId: String, expense: Expense)
}