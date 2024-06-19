package at.ac.fhcampuswien.budget_fox.models

import java.util.Date
import java.util.UUID

class Transaction(
    var uuid: String = "",
    var amount: Double = 0.0,
    var description: String = "",
    var date: Date = Date()
) {
    constructor(amount: Double, description: String, date: Date) : this() {
        uuid = UUID.randomUUID().toString()
        this.amount = amount
        this.description = description
        this.date = date
    }

    fun transactionToDatabase(): Map<String, Any> {
        return mapOf (
            "uuid" to this.uuid,
            "amount" to this.amount,
            "description" to this.description,
            "date" to date
        )
    }
}
