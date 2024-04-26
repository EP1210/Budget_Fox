package at.ac.fhcampuswien.budget_fox.models

import java.time.Period
import java.util.UUID

data class Income(
    val uuid: UUID = UUID.randomUUID(),
    val amount : Double,
    val description: String,
    val period: Period? = null // made period nullable to enable non periodical incomes
)
