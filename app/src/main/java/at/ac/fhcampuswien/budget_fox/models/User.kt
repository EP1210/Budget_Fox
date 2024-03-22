package at.ac.fhcampuswien.budget_fox.models

import kotlinx.datetime.LocalDate

data class User(
    var firstName: String = "",
    var lastName: String = "",
    var dateOfBirth: LocalDate = LocalDate(1970, 1, 2)
)