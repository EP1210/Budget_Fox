package at.ac.fhcampuswien.budget_fox.data

import at.ac.fhcampuswien.budget_fox.models.Household
import at.ac.fhcampuswien.budget_fox.models.Transaction

interface HouseholdDataAccessObject {

    fun insertHousehold(household: Household)

    fun getHousehold(householdId: String, onSuccess: (Household?) -> Unit)

    fun deleteHousehold(householdId: String)

    fun insertHouseholdTransaction(transaction: Transaction, householdId: String)

    fun getTransactionsFromHousehold(householdId: String, onSuccess: (List<Transaction>) -> Unit)

    fun joinHouseholdIfExist(
        userId: String,
        householdId: String,
        onSuccess: () -> Unit = {},
        notExits: () -> Unit = {}
    )
}