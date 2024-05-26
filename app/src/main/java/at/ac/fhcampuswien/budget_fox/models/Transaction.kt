package at.ac.fhcampuswien.budget_fox.models

import java.time.Period

data class Transaction(
    val uuid: String = "",
    val amount: Double = 0.0,
    val description: String = "",
    //val period: Period? = null // made period nullable to enable non periodical incomes //TODO: Period
)
