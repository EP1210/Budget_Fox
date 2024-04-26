package at.ac.fhcampuswien.budget_fox.models

import at.ac.fhcampuswien.budget_fox.data.UserRepository
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneOffset

class User(
    var firstName: String = "",
    var lastName: String = "",
    var dateOfBirthInEpoch: Long = 0,
    var dateOfRegistrationInEpoch: Long = 0,

    private val _incomes : MutableList<Income> = mutableListOf()
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

    fun userToDatabase(uid: String): Map<String, Any> {
        val userRepository = UserRepository()

        userRepository.addIncome(income = Income(amount = 10.5f, description = "FHCW", period = Period.ZERO), uid = uid)
        return mapOf(
            "firstName" to this.firstName,
            "lastName" to this.lastName,
            "dateOfBirthInEpoch" to this.dateOfBirthInEpoch,
            "dateOfRegistrationInEpoch" to this.dateOfRegistrationInEpoch,
        )
    }
}