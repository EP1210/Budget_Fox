package at.ac.fhcampuswien.budget_fox.models

import java.util.UUID

class Category(
    var uuid: String = "",
    var name: String = "",
    var description: String = "",
    var transactionMemberships: MutableList<String> = mutableListOf()
) {
    constructor(
        name: String,
        description: String
    ) : this() {
        uuid = UUID.randomUUID().toString()
        this.name = name
        this.description = description
    }
}