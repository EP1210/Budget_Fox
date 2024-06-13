package at.ac.fhcampuswien.budget_fox.models

import java.util.UUID


class Household(
    var uuid: String = "",
    var name: String = "",
    private val _transactions: MutableList<Transaction> = mutableListOf()
) {
    constructor(name: String) : this() {
        uuid = UUID.randomUUID().toString()
        this.name = name
    }

    fun addTransaction(transaction: Transaction) {
        _transactions.add(transaction)
    }

    fun getTransactions(): List<Transaction> {
        return _transactions
    }

    fun householdToDatabase(): Map<String, Any> {
        return mapOf(
            "uuid" to this.uuid,
            "name" to this.name
        )
    }
}