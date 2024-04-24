package at.ac.fhcampuswien.budget_fox.models

import java.time.LocalDateTime
import java.time.ZoneOffset

class User(var firstName: String = "", var lastName: String = "", var dateOfBirthInEpoch: Long = 0, var dateTimeOfRegistrationInEpoch: Long = 0) {
    constructor(firstName: String, lastName: String, dateOfBirth: LocalDateTime, dateTimeOfRegistration: LocalDateTime) : this() {
        this.firstName = firstName
        this.lastName = lastName
        this.dateOfBirthInEpoch = dateOfBirth.toEpochSecond(ZoneOffset.UTC)
        this.dateTimeOfRegistrationInEpoch = dateTimeOfRegistration.toEpochSecond(ZoneOffset.UTC)
    }
}