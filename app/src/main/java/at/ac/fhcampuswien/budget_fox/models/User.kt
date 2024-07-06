package at.ac.fhcampuswien.budget_fox.models

import java.time.LocalDateTime
import java.time.ZoneOffset

class User(
    var firstName: String = "",
    var lastName: String = "",
    var dateOfBirthInEpoch: Long = 0,
    var dateOfRegistrationInEpoch: Long = 0,
    private val _transactions: MutableList<Transaction> = mutableListOf(),
    private val _categories: MutableList<Category> = mutableListOf(),
    var householdId: String = ""
) {
    constructor(
        firstName: String,
        lastName: String,
        dateOfBirth: LocalDateTime,
        dateTimeOfRegistration: LocalDateTime
    ) : this() {
        this.firstName = firstName
        this.lastName = lastName
        this.dateOfBirthInEpoch = dateOfBirth.toEpochSecond(ZoneOffset.UTC)
        this.dateOfRegistrationInEpoch = dateTimeOfRegistration.toEpochSecond(ZoneOffset.UTC)
    }

    fun addTransaction(transaction: Transaction) {
        _transactions.add(transaction)
    }

    fun joinHousehold(householdId: String) {
        this.householdId = householdId
    }

    fun userToDatabase(): Map<String, Any> {
        return mapOf(
            "firstName" to this.firstName,
            "lastName" to this.lastName,
            "dateOfBirthInEpoch" to this.dateOfBirthInEpoch,
            "dateOfRegistrationInEpoch" to this.dateOfRegistrationInEpoch,
            "householdId" to this.householdId
        )
    }
}