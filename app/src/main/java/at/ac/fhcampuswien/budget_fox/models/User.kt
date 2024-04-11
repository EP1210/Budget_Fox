package at.ac.fhcampuswien.budget_fox.models

import java.time.LocalDateTime

data class User(
    var firstName: String = "",
    var lastName: String = "",
    var dateOfBirth: LocalDateTime = LocalDateTime.of(1970, 1, 1, 0, 0)
)