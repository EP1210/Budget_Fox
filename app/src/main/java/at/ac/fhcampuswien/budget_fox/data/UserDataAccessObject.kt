package at.ac.fhcampuswien.budget_fox.data

import at.ac.fhcampuswien.budget_fox.models.Income
import at.ac.fhcampuswien.budget_fox.models.User

interface UserDataAccessObject {

    fun addUser(user: User, uid: String)

    fun getUser(uid: String): User?

    fun deleteUser(uid: String)

    fun addIncome(income: Income, uid: String)

    fun deleteIncome(income: Income, uid: String)
}