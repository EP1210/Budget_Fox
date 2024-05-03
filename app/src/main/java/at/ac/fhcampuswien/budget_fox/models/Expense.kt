package at.ac.fhcampuswien.budget_fox.models

import java.util.UUID

data class Expense(
    val uuid: UUID = UUID.randomUUID(),
    val date: String,
    val amount: String,
    val description: String
)