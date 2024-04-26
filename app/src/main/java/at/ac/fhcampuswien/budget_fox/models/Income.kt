package at.ac.fhcampuswien.budget_fox.models

import java.time.Period
import java.util.UUID

data class Income(
    val uid: UUID = UUID.randomUUID(),
    val amount : Float,
    val description: String,
    val period: Period
)
