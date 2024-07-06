package at.ac.fhcampuswien.budget_fox.models

import com.google.firebase.Timestamp
import java.util.Date
import java.util.UUID

class Transaction(
    var uuid: String = "",
    var amount: Double = 0.0,
    var description: String = "",
    var date: Date = Date(),
    var isRegular: Boolean = false,
    var frequency: String? = null, // "daily", "weekly", "monthly", "yearly"
    var nextDueDate: Date? = null
) {
    constructor(amount: Double, description: String, date: Date) : this() {
        uuid = UUID.randomUUID().toString()
        this.amount = amount
        this.description = description
        this.date = date
    }

    constructor(
        amount: Double,
        description: String,
        date: Date,
        isRegular: Boolean,
        frequency: String?,
        nextDueDate: Date?
    ) : this() {
        uuid = UUID.randomUUID().toString()
        this.amount = amount
        this.description = description
        this.date = date
        this.isRegular = isRegular
        this.frequency = frequency
        this.nextDueDate = nextDueDate
    }

    fun transactionToDatabase(): Map<String, Any> {
        val dataMap = mutableMapOf(
            "uuid" to uuid,
            "amount" to amount,
            "description" to description,
            "date" to Timestamp(date),
            "isRegular" to isRegular
        )

        if (isRegular) {
            dataMap["frequency"] = frequency.orEmpty()
            dataMap["nextDueDate"] = nextDueDate?.let { Timestamp(it) } ?: Timestamp.now()
        }

        return dataMap
    }

    companion object {
        fun fromDatabase(data: Map<String, Any>): Transaction {
            val uuid = data["uuid"] as String
            val amount = (data["amount"] as Double?) ?: 0.0
            val description = (data["description"] as String?) ?: ""
            val date = (data["date"] as Timestamp).toDate()
            val isRegular = data["isRegular"] as Boolean
            val frequency = data["frequency"] as String?
            val nextDueDate = (data["nextDueDate"] as Timestamp?)?.toDate()

            return if (isRegular) {
                Transaction(uuid, amount, description, date, isRegular, frequency, nextDueDate)
            } else {
                Transaction(uuid, amount, description, date)
            }
        }
    }
}
