package at.ac.fhcampuswien.budget_fox.models

import java.util.UUID

class SavingGoal(
    var uuid: String = "",
    var name: String = "",
    var amount: Double = 0.0,
    var isDone: Boolean = false,
    private val _transactions: MutableList<Transaction> = mutableListOf()
) {
    constructor(name: String, amount: Double) : this() {
        this.uuid = UUID.randomUUID().toString()
        this.name = name
        this.amount = amount
    }

    fun getProgress(): Double {
        var sum = 0.0
        _transactions.forEach { transaction ->
            sum += transaction.amount
        }
        return sum
    }

    fun addTransaction(transaction: Transaction) {
        _transactions.add(transaction)
    }

    fun getTransactions(): List<Transaction> {
        return _transactions
    }

    fun savingGoalToDatabase(): Map<String, Any> {
        return mapOf(
            "uuid" to this.uuid,
            "name" to this.name,
            "amount" to this.amount,
            "done" to this.isDone
        )
    }
}