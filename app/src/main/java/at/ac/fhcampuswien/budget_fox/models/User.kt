package at.ac.fhcampuswien.budget_fox.models

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

class User(
    var firstName: String = "",
    var lastName: String = "",
    var dateOfBirthInEpoch: Long = 0,
    var dateOfRegistrationInEpoch: Long = 0,

    private var uid: String = "",
    private val _incomes : MutableList<Income> = mutableListOf(),
    private val _expenses: MutableList<Expense> = mutableListOf()
) {
    constructor(firstName: String, lastName: String, dateOfBirth: LocalDateTime, dateTimeOfRegistration: LocalDateTime) : this() {
        this.firstName = firstName
        this.lastName = lastName
        this.dateOfBirthInEpoch = dateOfBirth.toEpochSecond(ZoneOffset.UTC)
        this.dateOfRegistrationInEpoch = dateTimeOfRegistration.toEpochSecond(ZoneOffset.UTC)
    }

    fun addIncome(income: Income) {
        _incomes.add(income)
    }

    fun addExpense(expense: Expense) {
        _expenses.add(expense)
    }

    fun getUid() : String {
        return uid
    }

    fun setUid(uid: String) {
        this.uid = uid
    }

    fun userToDatabase(uid: String): Map<String, Any> {
        return mapOf(
            "firstName" to this.firstName,
            "lastName" to this.lastName,
            "dateOfBirthInEpoch" to this.dateOfBirthInEpoch,
            "dateOfRegistrationInEpoch" to this.dateOfRegistrationInEpoch,
        )
    }
}