package at.ac.fhcampuswien.budget_fox.models

import java.time.Period

data class Income(
    val amount : Float,
    val description: String,
    val period: Period
)
